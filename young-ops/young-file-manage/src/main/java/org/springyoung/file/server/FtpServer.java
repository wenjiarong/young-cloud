package org.springyoung.file.server;

import org.springyoung.file.entity.UserInfo;
import org.springyoung.file.handler.FtpHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class FtpServer extends Thread {

    public ServerSocket ctrlSocket;
    public static String initDir;  // 保存服务器线程运行时所在的工作目录
    public static List users = new ArrayList();
    public static List<UserInfo> usersInfo = new ArrayList();
    private String key;
    private String userCfg;

    public FtpServer(String userCfg, String key) {
        this.key = key;
        this.userCfg = userCfg;
        loadUsersInfo(userCfg);
    }

    @Override
    public void run() {
        int i = 0;
        try {
            // 监听2221号端口,2221口用于控制,2220口用于传数据
            ctrlSocket = new ServerSocket(2221);
            for (; ; ) {
                Socket ctrl = ctrlSocket.accept();
                PrintWriter writer = new PrintWriter(ctrl.getOutputStream(),
                        true);  // 文本文本输出流
                writer.println("220 服务准备");  // 命令正确的提示

                // 创建服务线程
                FtpHandler h = new FtpHandler(ctrl, i);
                h.start();
                users.add(h);  // 将此用户线程加入到这个 ArrayList 中
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // loadUsersInfo方法
    // 把文件中的用户信息(用户名,密码,路径)加载到UserInfo 这个 ArrayList 中
    public void loadUsersInfo(String userCfg) {
        int p1; // 放 | 的索引
        int p2; // 放 | 后一位的索引
        // 当前 File 是否存在
        if (new File(userCfg).exists()) {
            try {
                BufferedReader fin = new BufferedReader(new InputStreamReader(
                        new FileInputStream(userCfg)));
                String line; // 从文件中取一行存于此
                String field; // 放 | 前 line 的子串
                int i = 0;
                // 第一个while 作用为读所有行
                // 达流尾则为 null
                while ((line = fin.readLine()) != null) {
                    UserInfo temp = new UserInfo();
                    p1 = 0;
                    i = 0;
                    if (line.startsWith("#"))// 如以#开始,返回ture
                        continue;
                    // 第二个while 作用为load 文件中一行的信息
                    // 从p1开始查,返回 |
                    // 第一次出现的索引,没有返回-1
                    while ((p2 = line.indexOf("|", p1)) != -1) {
                        field = line.substring(p1, p2);// 从p1 ~ p2-1
                        p2 = p2 + 1;
                        p1 = p2; // 新p2
                        switch (i) {
                            case 0:
                                temp.setAccount(field);
                                break;
                            case 1:
                                temp.setPassword(field);
                                break;
                            case 2:
                                temp.setWorkDir(field);
                                break;
                        }
                        i++;
                    }
                    usersInfo.add(temp);
                }
                fin.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getUserCfg() {
        return userCfg;
    }

    public String getKey() {
        return key;
    }

}
