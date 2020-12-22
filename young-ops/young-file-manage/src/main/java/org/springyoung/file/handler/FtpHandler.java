package org.springyoung.file.handler;

import lombok.extern.slf4j.Slf4j;
import org.springyoung.file.constant.FtpState;
import org.springyoung.file.server.FtpServer;
import org.springyoung.file.utils.FileUtil;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class FtpHandler extends Thread {
	Socket ctrlSocket;     //用于控制的套接字
	Socket dataSocket;     //用于传输的套接字
	int id;
	String cmd = "";        //存放指令(空格前)
	String param = "";        //放当前指令之后的参数(空格后)
	String user;
	String remoteHost = "";       //客户IP
	int remotePort = 0;               //客户TCP 端口号
	String dir = FtpServer.initDir;//当前目录
	String rootdir = "c:/";           //默认根目录,在checkPASS中设置
	int state = 0;                   //用户状态标识符,在checkPASS中设置
	String reply;                   //返回报告
	PrintWriter ctrlOutput;
	int type = 0;                   //文件类型(ascII 或 bin)
	String requestfile = "";
	boolean isrest = false;

	//FtpHandler方法
	//构造方法
	public FtpHandler(Socket s, int i) {
		ctrlSocket = s;
		id = i;
	}

	//run 方法
	public void run() {
		String str;
		//与cmd 一一对应的号
		int parseResult;
		try {
			BufferedReader ctrlInput = new BufferedReader
				(new InputStreamReader(ctrlSocket.getInputStream()));
			ctrlOutput = new PrintWriter(ctrlSocket.getOutputStream(), true);
			state = FtpState.FS_WAIT_LOGIN;
			boolean finished = false;
			while (!finished) {
				str = ctrlInput.readLine();
				//跳出while
				if (str == null) finished = true;
				else {
					//指令转化为指令号
					parseResult = parseInput(str);
					log.info("指令:" + cmd + " 参数:" + param);
					//用户状态开关
					switch (state) {
						case FtpState.FS_WAIT_LOGIN:
							finished = commandUSER();
							break;
						case FtpState.FS_WAIT_PASS:
							finished = commandPASS();
							break;
						case FtpState.FS_LOGIN: {
							//指令号开关,决定程序是否继续运行的关键
							switch (parseResult) {
								case -1:
									errCMD();                    //语法错
									break;
								case 4:
									finished = commandCDUP();   //到上一层目录
									break;
								case 6:
									finished = commandCWD();    //到指定的目录
									break;
								case 7:
									finished = commandQUIT();    //退出
									break;
								case 9:
									finished = commandPORT();    //客户端IP:地址+TCP 端口号
									break;
								case 11:
									finished = commandTYPE();    //文件类型设置(ascII 或 bin)
									break;
								case 14:
									finished = commandRETR();    //从服务器中获得文件
									break;
								case 15:
									finished = commandSTOR();    //向服务器中发送文件
									break;
								case 22:
									finished = commandABOR();    //关闭传输用连接dataSocket
									break;
								case 23:
									finished = commandDELE();    //删除服务器上的指定文件
									break;
								case 25:
									finished = commandMKD();    //建立目录
									break;
								case 27:
									finished = commandLIST();    //文件和目录的列表
									break;
								case 26:
								case 33:
									finished = commandPWD();    //"当前目录" 信息
									break;
								case 32:
									finished = commandNOOP();    //"命令正确" 信息
									break;
							}
						}
						break;
					}
				}
				ctrlOutput.println(reply);
				ctrlOutput.flush();
			}
			ctrlSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//parseInput方法
	int parseInput(String s) {
		int p;
		int i = -1;
		p = s.indexOf(" ");
		if (p == -1)                  //如果是无参数命令(无空格)
			cmd = s;
		else
			cmd = s.substring(0, p);  //有参数命令,过滤参数

		if (p >= s.length() || p == -1)//如果无空格,或空格在读入的s串最后或之外
			param = "";
		else
			param = s.substring(p + 1);
		cmd = cmd.toUpperCase();     //转换该 String 为大写

		if (cmd.equals("CDUP"))
			i = 4;
		if (cmd.equals("CWD"))
			i = 6;
		if (cmd.equals("QUIT"))
			i = 7;
		if (cmd.equals("PORT"))
			i = 9;
		if (cmd.equals("TYPE"))
			i = 11;
		if (cmd.equals("RETR"))
			i = 14;
		if (cmd.equals("STOR"))
			i = 15;
		if (cmd.equals("ABOR"))
			i = 22;
		if (cmd.equals("DELE"))
			i = 23;
		if (cmd.equals("MKD"))
			i = 25;
		if (cmd.equals("PWD"))
			i = 26;
		if (cmd.equals("LIST"))
			i = 27;
		if (cmd.equals("NOOP"))
			i = 32;
		if (cmd.equals("XPWD"))
			i = 33;
		return i;
	}

	//validatePath方法
	//判断路径的属性,返回 int
	int validatePath(String s) {
		File f = new File(s);        //相对路径
		if (f.exists() && !f.isDirectory()) {
			String s1 = s.toLowerCase();
			String s2 = rootdir.toLowerCase();
			if (s1.startsWith(s2))
				return 1;            //文件存在且不是路径,且以rootdir 开始
			else {
				return 0;            //文件存在且不是路径,不以rootdir 开始
			}
		}
		f = new File(FileUtil.addTail(dir) + s);//绝对路径
		if (f.exists() && !f.isDirectory()) {
			String s1 = (FileUtil.addTail(dir) + s).toLowerCase();
			String s2 = rootdir.toLowerCase();
			if (s1.startsWith(s2))
				return 2;            //文件存在且不是路径,且以rootdir 开始
			else {
				return 0;            //文件存在且不是路径,不以rootdir 开始
			}
		}
		return 0;                    //其他情况
	}

	//检查密码是否正确,从文件中找
	boolean checkPASS(String s) {
		for (int i = 0; i < FtpServer.usersInfo.size(); i++) {
			if ((FtpServer.usersInfo.get(i)).getAccount().equals(user) &&
				(FtpServer.usersInfo.get(i)).getPassword().equals(s)) {
				rootdir = (FtpServer.usersInfo.get(i)).getWorkDir();
				dir = (FtpServer.usersInfo.get(i)).getWorkDir();
				return true;
			}
		}
		return false;
	}

	//commandUSER方法
	//用户名是否正确
	boolean commandUSER() {
		if (cmd.equals("USER")) {
			reply = "331 用户名正确,需要口令";
			user = param;
			state = FtpState.FS_WAIT_PASS;
			return false;
		} else {
			reply = "501 参数语法错误,用户名不匹配";
			return true;
		}
	}

	//commandPASS 方法
	//密码是否正确
	boolean commandPASS() {
		if (cmd.equals("PASS")) {
			if (checkPASS(param)) {
				reply = "230 用户登录了";
				state = FtpState.FS_LOGIN;
				log.info("新消息: 用户: " + user + " 来自于: " + remoteHost + "登录了");
				return false;
			} else {
				reply = "530 没有登录";
				return true;
			}
		} else {
			reply = "501 参数语法错误,密码不匹配";
			return true;
		}
	}

	void errCMD() {
		reply = "500 语法错误";
	}

	//到上一层目录
	boolean commandCDUP() {
		dir = FtpServer.initDir;
		File f = new File(dir);
		if (f.getParent() != null && (!dir.equals(rootdir)))//有父路径 && 不是根路径
		{
			dir = f.getParent();
			reply = "200 命令正确";
		} else {
			reply = "550 当前目录无父路径";
		}
		return false;
	}

	//该命令改变工作目录到用户指定的目录
	boolean commandCWD() {
		File f = new File(param);
		String s;
		String s1;
		if (dir.endsWith("/"))
			s = dir;
		else
			s = dir + "/";
		File f1 = new File(s + param);

		if (f.isDirectory() && f.exists()) {
			if (param.equals("..") || param.equals("..\\")) {
				if (dir.compareToIgnoreCase(rootdir) == 0) {
					reply = "550 此路径不存在";
				} else {
					s1 = new File(dir).getParent();
					if (s1 != null) {
						dir = s1;
						reply = "250 请求的文件处理结束, 当前目录变为: " + dir;
					} else
						reply = "550 此路径不存在";
				}
			} else if (param.equals(".") || param.equals(".\\")) {
			} else {
				dir = param;
				reply = "250 请求的文件处理结束, 工作路径变为 " + dir;
			}
		} else if (f1.isDirectory() && f1.exists()) {
			dir = s + param;
			reply = "250 请求的文件处理结束, 工作路径变为 " + dir;
		} else {
			reply = "501 参数语法错误";
		}
		return false;
	}

	boolean commandQUIT() {
		reply = "221 服务关闭连接";
		return true;
	}

	/*
	 *使用该命令时，客户端必须发送客户端用于接收数据的32位IP 地址和16位 的TCP 端口号。
	 *这些信息以8位为一组，使用十进制传输，中间用逗号隔开。
	 */
	boolean commandPORT() {
		int p1 = 0;
		int p2;
		int[] a = new int[6];//存放ip+tcp
		int i = 0;
		try {
			//前5位
			while ((p2 = param.indexOf(",", p1)) != -1) {
				a[i] = Integer.parseInt(param.substring(p1, p2));
				p2 = p2 + 1;
				p1 = p2;
				i++;
			}
			a[i] = Integer.parseInt(param.substring(p1, param.length()));//最后一位
		} catch (NumberFormatException e) {
			reply = "501 参数语法错误";
			return false;
		}

		remoteHost = a[0] + "." + a[1] + "." + a[2] + "." + a[3];
		remotePort = a[4] * 256 + a[5];
		reply = "200 命令正确";
		return false;
	}

	/*LIST 命令用于向客户端返回服务器中工作目录下的目录结构，包括文件和目录的列表。
	 *处理这个命令时，先创建一个临时的套接字向客户端发送目录信息。这个套接字的目的
	 *端口号缺省为，然后为当前工作目录创建File 对象，利用该对象的list()方法得到一
	 *个包含该目录下所有文件和子目录名称的字符串数组，然后根据名称中是否含有文件名
	 *中特有的"."来区别目录和文件。最后，将得到的名称数组通过临时套接字发送到客户端。
	 **/
	//文件和目录的列表
	boolean commandLIST() {
		try {
			dataSocket = new Socket();
			dataSocket.setReuseAddress(true);
			SocketAddress socketAddress = new InetSocketAddress(remoteHost, remotePort);
			dataSocket.connect(socketAddress);
			PrintWriter dout = new PrintWriter(dataSocket.getOutputStream(), true);
			if (param.equals("") || param.equals("LIST")) {
				ctrlOutput.println("150 文件状态正常,ls以 ASCII 方式操作");
				File f = new File(dir);
				String[] dirStructure = f.list();//指定路径中的文件名数组,不包括当前路径或父路径
				String file;
				for (int i = 0; i < dirStructure.length; i++) {
					f = new File(dir + FileUtil.addHead(dirStructure[i]));
					if (dirStructure[i].indexOf(".") != -1) {
						//父目录(在linux下)
						file = "-rw-r--r-- 1 " + user + " " + user + " " + f.length() + " " + longToString_Time(f.lastModified()) + " " + dirStructure[i];
					} else {
						//本目录的文件和子目录
						file = "drwxr-xr-x 2 " + user + " " + user + " " + f.length() + " " + longToString_Time(f.lastModified()) + " " + dirStructure[i];
					}
					dout.println(file);
				}
			}
			dout.close();
			dataSocket.close();
			reply = "226 传输数据连接结束";
		} catch (Exception e) {
			e.printStackTrace();
			reply = "451 Requested action aborted: local error in processing";
			return false;
		}
		return false;
	}

	//TYPE 命令用来完成类型设置
	boolean commandTYPE() {
		if (param.equals("A")) {
			type = FtpState.FTYPE_ASCII;//0
			reply = "200 命令正确 ,转 ASCII 模式";
		} else if (param.equals("I")) {
			type = FtpState.FTYPE_IMAGE;//1
			reply = "200 命令正确 转 BINARY 模式";
		} else
			reply = "504 命令不能执行这种参数";
		return false;
	}

	//connamdRETR 方法
	//从服务器中获得文件
	boolean commandRETR() {
		requestfile = param;
		File f = new File(requestfile);
		if (!f.exists()) {
			f = new File(FileUtil.addTail(dir) + param);
			if (!f.exists()) {
				reply = "550 文件不存在";
				return false;
			}
			requestfile = FileUtil.addTail(dir) + param;
		}
		if (isrest) {
		} else {
			//bin
			if (type == FtpState.FTYPE_IMAGE) {
				try {
					ctrlOutput.println("150 文件状态正常,以二进治方式打开文件:  " + requestfile);
					dataSocket = new Socket();
					dataSocket.setReuseAddress(true);
					SocketAddress socketAddress = new InetSocketAddress(remoteHost, remotePort);
					dataSocket.connect(socketAddress);
					BufferedInputStream fin = new BufferedInputStream(new FileInputStream(requestfile));
					PrintStream dataOutput = new PrintStream(dataSocket.getOutputStream(), true);
					byte[] buf = new byte[1024];         //目标缓冲区
					int l;
					//缓冲区未读满
					while ((l = fin.read(buf, 0, 1024)) != -1) {
						//写入套接字
						dataOutput.write(buf, 0, l);
					}
					fin.close();
					dataOutput.close();
					dataSocket.close();
					reply = "226 传输数据连接结束";
				} catch (Exception e) {
					e.printStackTrace();
					reply = "451 请求失败: 传输出故障";
					return false;
				}
			}
			//ascII
			if (type == FtpState.FTYPE_ASCII) {
				try {
					ctrlOutput.println("150 Opening ASCII mode data connection for " + requestfile);
					dataSocket = new Socket();
					dataSocket.setReuseAddress(true);
					SocketAddress socketAddress = new InetSocketAddress(remoteHost, remotePort);
					dataSocket.connect(socketAddress);
					BufferedReader fin = new BufferedReader(new FileReader(requestfile));
					PrintWriter dataOutput = new PrintWriter(dataSocket.getOutputStream(), true);
					String s;
					while ((s = fin.readLine()) != null) {
						dataOutput.println(s);
					}
					fin.close();
					dataOutput.close();
					dataSocket.close();
					reply = "226 传输数据连接结束";
				} catch (Exception e) {
					e.printStackTrace();
					reply = "451 请求失败: 传输出故障";
					return false;
				}
			}
		}
		return false;
	}

	//commandSTOR 方法
	//向服务器中发送文件STOR
	boolean commandSTOR() {
		if (param.equals("")) {
			reply = "501 参数语法错误";
			return false;
		}
		requestfile = FileUtil.addTail(dir) + param;
		//bin
		if (type == FtpState.FTYPE_IMAGE) {
			try {
				ctrlOutput.println("150 Opening Binary mode data connection for " + requestfile);
				dataSocket = new Socket();
				dataSocket.setReuseAddress(true);
				SocketAddress socketAddress = new InetSocketAddress(remoteHost, remotePort);
				dataSocket.connect(socketAddress);
				BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(requestfile));
				BufferedInputStream dataInput = new BufferedInputStream(dataSocket.getInputStream());
				byte[] buf = new byte[1024];
				int l = 0;
				while ((l = dataInput.read(buf, 0, 1024)) != -1) {
					fout.write(buf, 0, l);
				}
				dataInput.close();
				fout.close();
				dataSocket.close();
				reply = "226 传输数据连接结束";
			} catch (Exception e) {
				e.printStackTrace();
				reply = "451 请求失败: 传输出故障";
				return false;
			}
		}
		//ascII
		if (type == FtpState.FTYPE_ASCII) {
			try {
				ctrlOutput.println("150 Opening ASCII mode data connection for " + requestfile);
				dataSocket = new Socket();
				dataSocket.setReuseAddress(true);
				SocketAddress socketAddress = new InetSocketAddress(remoteHost, remotePort);
				dataSocket.connect(socketAddress);
				PrintWriter fout = new PrintWriter(new FileOutputStream(requestfile));
				BufferedReader dataInput = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
				String line;
				while ((line = dataInput.readLine()) != null) {
					fout.println(line);
				}
				dataInput.close();
				fout.close();
				dataSocket.close();
				reply = "226 传输数据连接结束";
			} catch (Exception e) {
				e.printStackTrace();
				reply = "451 请求失败: 传输出故障";
				return false;
			}
		}
		return false;
	}

	boolean commandPWD() {
		reply = "257 " + dir + " 是当前目录.";
		return false;
	}

	boolean commandNOOP() {
		reply = "200 命令正确.";
		return false;
	}

	//强关dataSocket 流
	boolean commandABOR() {
		try {
			dataSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
			reply = "451 请求失败: 传输出故障";
			return false;
		}
		reply = "421 服务不可用, 关闭数据传送连接";
		return false;
	}

	//删除服务器上的指定文件
	boolean commandDELE() {
		int i = validatePath(param);
		if (i == 0) {
			reply = "550 请求的动作未执行,文件不存在,或目录不对,或其他";
			return false;
		}
		if (i == 1) {
			File f = new File(param);
			f.delete();
		}
		if (i == 2) {
			File f = new File(FileUtil.addTail(dir) + param);
			f.delete();
		}
		reply = "250 请求的文件处理结束,成功删除服务器上文件";
		return false;
	}

	//建立目录,要绝对路径
	boolean commandMKD() {
		String s1 = param.toLowerCase();
		String s2 = rootdir.toLowerCase();
		if (s1.startsWith(s2)) {
			File f = new File(param);
			if (f.exists()) {
				reply = "550 请求的动作未执行,目录已存在";
				return false;
			} else {
				f.mkdirs();
				reply = "250 请求的文件处理结束, 目录建立";
			}
		} else {
			File f = new File(FileUtil.addTail(dir) + param);
			if (f.exists()) {
				reply = "550 请求的动作未执行,目录已存在";
				return false;
			} else {
				f.mkdirs();
				reply = "250 请求的文件处理结束, 目录建立";
			}
		}
		return false;
	}

	/**
	 * 将long时间类型转换成String
	 *
	 * @param time
	 * @return
	 */
	public String longToString_Time(long time) {
		SimpleDateFormat dafo = new SimpleDateFormat("yyyy-MM-dd");
		return dafo.format(new Date(time));
	}

}
