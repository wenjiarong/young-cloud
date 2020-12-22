package org.springyoung.file.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springyoung.core.boot.ctrl.YoungController;
import org.springyoung.core.constant.IsDeletedEnum;
import org.springyoung.core.secure.YoungUser;
import org.springyoung.core.tool.api.R;
import org.springyoung.core.tool.utils.ObjectUtil;
import org.springyoung.file.config.MyProperties;
import org.springyoung.file.config.StartService;
import org.springyoung.file.entity.FileInfo;
import org.springyoung.file.entity.FileServer;
import org.springyoung.file.entity.FtpFile;
import org.springyoung.file.entity.UserInfo;
import org.springyoung.file.handler.code.ErrorCode;
import org.springyoung.file.server.FtpServer;
import org.springyoung.file.service.IFileInfoService;
import org.springyoung.file.service.IFileServerService;
import org.springyoung.file.utils.FileUtil;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @ClassName ServerController
 * @Description TODO
 * @Author 小温
 * @Date 2020/12/9 18:00
 * @Version 1.0
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/ftp")
@Api(value = "ftp", tags = "ftp")
public class FtpController extends YoungController {

    private final MyProperties properties;
    private final IFileInfoService fileInfoService;
    private final IFileServerService fileServerService;

    @PostMapping("/ftp-cmd")
    @ApiOperationSupport(order = 1)
    public R ftpCmd(@RequestParam String ip
            , @RequestParam String port
            , @RequestParam String account
            , @RequestParam String passWord
            , @RequestParam String sign
            , @RequestParam String fileServerId
            , String ownPath
            , String othersPath
            , String fileName
            , String tenantId) throws Exception {

        if (StringUtils.isNotEmpty(fileServerId)) {
            QueryWrapper<FileServer> qw = new QueryWrapper<>();
            LambdaQueryWrapper<FileServer> eq = qw.lambda()
                    .eq(FileServer::getId, fileServerId)
                    .eq(FileServer::getIsDeleted, IsDeletedEnum.no.getStatus());
            FileServer fileServer = fileServerService.getOne(eq);
            ip = fileServer.getIp();
            port = fileServer.getPort().toString();
            account = fileServer.getUsername();
            passWord = fileServer.getPassword();
        }

        YoungUser user = getUser();
        if (StringUtils.isEmpty(tenantId)) {
            tenantId = user.getTenantId();
        }

        ServerSocket serverSocket = null;
        PrintWriter ctrlWriter = null;
        Socket ctrlSocket = null;
        Scanner ctrlScanner;
        InetAddress addr;
        String resultCode;
        String resultStr;
        List<FtpFile> files;

        try {
            try {
                addr = Inet4Address.getByName(ip);
            } catch (UnknownHostException e) {
                return R.fail(ErrorCode.THE_INPUT_IP_IS_NOT_RECOGNIZED);
            }

            ctrlSocket = new Socket(addr, Integer.valueOf(port));
            ctrlWriter = new PrintWriter(ctrlSocket.getOutputStream(), true);
            ctrlScanner = new Scanner(ctrlSocket.getInputStream());

            resultStr = ctrlScanner.nextLine();
            resultCode = interpretCode(resultStr);
            if ("220".equals(resultCode)) {
                ctrlWriter.println("USER " + account);
            } else {
                do_quit(ctrlSocket, ctrlWriter, serverSocket);
                return R.fail(ErrorCode.CONNECTION_FAILED);
            }
            resultStr = ctrlScanner.nextLine();
            resultCode = interpretCode(resultStr);
            if ("331".equals(resultCode)) {
                ctrlWriter.println("PASS " + passWord);
            } else {
                do_quit(ctrlSocket, ctrlWriter, serverSocket);
                return R.fail(ErrorCode.CONNECTION_FAILED);
            }
            resultStr = ctrlScanner.nextLine();
            resultCode = interpretCode(resultStr);
            if ("230".equals(resultCode)) {
                log.info("登录成功");
            } else {
                do_quit(ctrlSocket, ctrlWriter, serverSocket);
                return R.fail(ErrorCode.CONNECTION_FAILED);
            }
            log.info("控制连接已经建立在 " + ctrlSocket.getInetAddress() + " 端口: " + ctrlSocket.getPort());
            ctrlWriter.println("TYPE I");
            resultStr = ctrlScanner.nextLine();
            resultCode = interpretCode(resultStr);
            if ("200".equals(resultCode)) {
                serverSocket = new ServerSocket(properties.getPort());
                ctrlWriter.println(preparePort(ctrlSocket.getLocalAddress(), properties.getPort()));
            } else {
                do_quit(ctrlSocket, ctrlWriter, serverSocket);
                return R.fail(ErrorCode.THE_ATTACHED_SERVER_DOES_NOT_SUPPORT_ASCII_TRANSPORT);
            }
            switch (sign) {
                case "put":
                    if (do_put(ctrlWriter, ctrlScanner, serverSocket, tenantId + FileUtil.addHead(ownPath), fileName)) {
                    } else {
                        do_quit(ctrlSocket, ctrlWriter, serverSocket);
                        return R.fail(ErrorCode.THE_CONNECTED_SERVER_DIRECTORY_OR_FILE_DOES_NOT_EXIST);
                    }
                    break;
                case "get":
                    if (do_get(ctrlWriter, ctrlScanner, serverSocket, tenantId + FileUtil.addHead(ownPath), fileName, othersPath)) {
                    } else {
                        do_quit(ctrlSocket, ctrlWriter, serverSocket);
                        return R.fail(ErrorCode.THE_DIRECTORY_OR_FILE_TO_DOWNLOAD_DOES_NOT_EXIST);
                    }
                    break;
                case "list":
                    files = new ArrayList<>();
                    do_list(files, ctrlWriter, ctrlScanner, serverSocket);
                    do_quit(ctrlSocket, ctrlWriter, serverSocket);
                    return R.data(files);
                case "cwd":
                    if (do_cwd(ctrlWriter, ctrlScanner, tenantId + FileUtil.addHead(othersPath))) {
                        files = new ArrayList<>();
                        do_list(files, ctrlWriter, ctrlScanner, serverSocket);
                        do_quit(ctrlSocket, ctrlWriter, serverSocket);
                        return R.data(files);
                    } else {
                        do_quit(ctrlSocket, ctrlWriter, serverSocket);
                        return R.fail(ErrorCode.THIS_PATH_DOES_NOT_EXIST);
                    }
                default:
                    do_quit(ctrlSocket, ctrlWriter, serverSocket);
                    return R.fail(ErrorCode.INVALID_COMMAND);
            }
        } catch (Exception e) {
            do_quit(ctrlSocket, ctrlWriter, serverSocket);
            return R.fail(ErrorCode.FILE_SERVER_ERROR_PLEASE_TRY_AGAIN_LATER);
        }
        do_quit(ctrlSocket, ctrlWriter, serverSocket);
        return R.data(true);
    }


    @PostMapping("/save-or-update-user")
    @ApiOperationSupport(order = 2)
    public R saveOrUpdateUser(@RequestParam String account, @RequestParam String passWord, @RequestParam String workDir) {
        boolean flag = true;
        FtpServer ftpServer = (FtpServer) StartService.SERVER_MAP.get(StartService.FTP_SERVER_NAME);
        for (UserInfo user : FtpServer.usersInfo) {
            if (user.getAccount().equals(account)) {
                flag = false;
                user.setPassword(passWord);
                user.setWorkDir(workDir);
                break;
            }
        }
        if (flag) {
            ftpServer.usersInfo.add(new UserInfo(account, passWord, workDir));
        }
        writeCfg(ftpServer);
        return R.data(true);
    }


    @GetMapping("/user-list")
    @ApiOperationSupport(order = 3)
    public R userList() {
        FtpServer ftpServer = (FtpServer) StartService.SERVER_MAP.get(StartService.FTP_SERVER_NAME);
        return R.data(ftpServer.usersInfo);
    }


    @DeleteMapping("/remove-user")
    @ApiOperationSupport(order = 4)
    public R removeUser(@RequestParam String account) {
        FtpServer ftpServer = (FtpServer) StartService.SERVER_MAP.get(StartService.FTP_SERVER_NAME);
        for (UserInfo user : FtpServer.usersInfo) {
            if (user.getAccount().equals(account)) {
                FtpServer.usersInfo.remove(user);
                writeCfg(ftpServer);
                return R.data(true);
            }
        }
        return R.fail(ErrorCode.THE_USER_DOES_NOT_EXIST_AND_CANNOT_BE_DELETED);
    }


    private void writeCfg(FtpServer ftpServer) {
        String s;
        try {
            BufferedWriter fout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ftpServer.getUserCfg())));
            for (UserInfo user : ftpServer.usersInfo) {
                s = user.getAccount() + "|" + user.getPassword() + "|" + user.getWorkDir() + "|";
                fout.write(s);
                fout.newLine();
            }
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean do_get(PrintWriter ctrlWriter
            , Scanner ctrlScanner, ServerSocket serverSocket
            , String ownPath, String fileName, String othersPath) throws IOException {
        boolean result = false;

        if (StringUtils.isNotEmpty(othersPath)) {
            ctrlWriter.println("RETR " + othersPath + FileUtil.addHead(fileName));
        } else {
            ctrlWriter.println("RETR " + fileName);
        }
        String code = interpretCode(ctrlScanner.nextLine());
        if ("200".equals(code)) {
            code = interpretCode(ctrlScanner.nextLine());
            if ("150".equals(code)) {
                Socket dataSocket = serverSocket.accept();

                File inFile = new File(properties.getDir() + ownPath + FileUtil.addHead(fileName));
                File p = new File(inFile.getAbsolutePath());
                if (!p.exists()) {
                    p.mkdirs();
                }
                if (inFile.exists()) {
                    inFile.delete();
                }
                inFile.createNewFile();

                BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(inFile));
                BufferedInputStream dataInput = new BufferedInputStream(dataSocket.getInputStream());
                byte[] buf = new byte[1024];
                int l;
                while ((l = dataInput.read(buf, 0, 1024)) != -1) {
                    fout.write(buf, 0, l);
                }

                dataInput.close();
                fout.close();
                dataSocket.close();
                code = interpretCode(ctrlScanner.nextLine());
                if ("226".equals(code)) {
                    log.info("文件传输完毕");

                    String path = inFile.getAbsolutePath();
                    QueryWrapper<FileInfo> qw = new QueryWrapper<>();
                    LambdaQueryWrapper<FileInfo> eq = qw.lambda()
                            .eq(FileInfo::getFilePath, path)
                            .eq(FileInfo::getIsDeleted, IsDeletedEnum.no.getStatus());
                    FileInfo fileInfo = fileInfoService.getOne(eq);

                    if (ObjectUtil.isEmpty(fileInfo)) {
                        fileInfo = new FileInfo();
                        //此处服务器ID使用默认47
                        fileInfo.setFileServerId(Long.valueOf(FileServerController.HTTP_SERVER_IP_ID));
                        fileInfo.setFilePath(path);
                        fileInfo.setFileName(fileName);
                        fileInfo.setFileSize((int) inFile.length());
                        fileInfoService.save(fileInfo);
                    } else {
                        fileInfo.setFileServerId(Long.valueOf(FileServerController.HTTP_SERVER_IP_ID));
                        fileInfo.setFilePath(path);
                        fileInfo.setFileName(fileName);
                        fileInfo.setFileSize((int) inFile.length());
                        fileInfoService.updateById(fileInfo);
                    }
                    log.info("接收文件 " + fileName + " 完毕");
                    result = true;
                }
            }
        }
        return result;
    }

    private boolean do_put(PrintWriter ctrlWriter
            , Scanner ctrlScanner, ServerSocket serverSocket
            , String ownPath, String fileName) throws IOException {

        boolean result = false;
        ctrlWriter.println("STOR " + fileName);
        String code = interpretCode(ctrlScanner.nextLine());
        if ("200".equals(code)) {
            code = interpretCode(ctrlScanner.nextLine());
            if ("150".equals(code)) {
                Socket dataSocket = serverSocket.accept();

                File outFile = new File(properties.getDir() + ownPath + FileUtil.addHead(fileName));
                BufferedInputStream fin = new BufferedInputStream(new FileInputStream(outFile));
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
                code = interpretCode(ctrlScanner.nextLine());
                if ("226".equals(code)) {
                    log.info("上传文件 " + fileName + " 完毕");
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * 断开连接
     */
    private static void do_quit(Socket ctrlSocket, PrintWriter ctrlWriter, ServerSocket serverSocket) throws Exception {
        if (ObjectUtil.isNotEmpty(ctrlWriter)) {
            ctrlWriter.println("QUIT");//请求服务器断开连接
        }
        Thread.sleep(100);
        if (ObjectUtil.isNotEmpty(serverSocket)) {
            serverSocket.close();
        }
        if (ObjectUtil.isNotEmpty(ctrlSocket)) {
            ctrlSocket.close();
        }
    }

    private void do_list(List<FtpFile> files, PrintWriter ctrlWriter
            , Scanner ctrlScanner, ServerSocket serverSocket) throws IOException {
        ctrlWriter.println("LIST");
        String code = interpretCode(ctrlScanner.nextLine());
        if ("200".equals(code) || "150".equals(code)) {
            Socket dataSocket = serverSocket.accept();
            BufferedReader dataInput = new BufferedReader
                    (new InputStreamReader(dataSocket.getInputStream()));

            FtpFile ftpFile;
            String str = dataInput.readLine();

            while (ObjectUtil.isNotEmpty(str)) {
                ftpFile = new FtpFile();
                String[] array = str.split("\\s+");
                str = array[array.length - 1];
                ftpFile.setFileName(str);
                if (str.indexOf(".") > -1) {
                    ftpFile.setType(1);
                } else {
                    ftpFile.setType(0);
                }
                files.add(ftpFile);
                str = dataInput.readLine();
            }
        }
    }

    private boolean do_cwd(PrintWriter ctrlWriter, Scanner ctrlScanner, String othersPath) {
        ctrlWriter.println("CWD " + properties.getDir() + othersPath);
        String code = interpretCode(ctrlScanner.nextLine());
        if ("200".equals(code)) {
            code = interpretCode(ctrlScanner.nextLine());
        }
        return "250".equals(code);
    }

    /***
     * 从recvBuf中解析出服务器返回 的代码
     * 服务器返回字符串格式为 三位数字+一位空格+详细描述信息
     * 具体见RFC959
     * @param recvBuf 从服务器接收到的字符串信息
     */
    public String interpretCode(String recvBuf) {
        String resultStr = "";
        StringTokenizer token = new StringTokenizer(recvBuf, " ");
        if (token.hasMoreTokens()) {
            resultStr = token.nextToken();//空格前的三位数字码
        }
        return resultStr;
    }

    /**
     * 准备port指令，用来指定客户端的端口，表示“我打开了xxx端口，你用这个端口传数据”
     *
     * @param localAddress
     * @param port
     * @return
     */
    public String preparePort(InetAddress localAddress, int port) {
        StringBuilder builder = new StringBuilder();
        String prePort = String.valueOf(port / 256);
        String rearPort = String.valueOf(port % 256);
        String ip = localAddress.getHostAddress().replaceAll("\\.", ",");
        builder.append("PORT ");
        builder.append(ip);
        builder.append(",");
        builder.append(prePort);
        builder.append(",");
        builder.append(rearPort);
        return builder.toString();
    }

}
