package org.springyoung.file.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springyoung.file.server.FtpServer;
import org.springyoung.file.server.TftpServer;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 继承Application接口后项目启动时会按照执行顺序执行run方法
 * 通过设置Order的value来指定执行的顺序
 */
@Component
@Order(value = 1)
@AllArgsConstructor
public class StartService implements ApplicationRunner {

    private final MyProperties properties;

    public static ConcurrentHashMap<String, Object> SERVER_MAP = new ConcurrentHashMap<>();
    public static final String TFTP_SERVER_NAME = "tftp_server";
    public static final String FTP_SERVER_NAME = "ftp_server";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String rootDir = properties.getDir();
        File dir = new File(rootDir);

        TftpServer tftpServer = new TftpServer(dir, TFTP_SERVER_NAME);
        SERVER_MAP.put(tftpServer.getKey(), tftpServer);
        // 高级的配置
        // 允许读取
        //server.setAllowRead(true);
        // 不允许写入
        //server.setAllowWrite(false);
        // 不允许覆盖
        //server.setAllowOverwrite(false);
        tftpServer.start();

        FtpServer ftpServer = new FtpServer(properties.getUserCfg(), FTP_SERVER_NAME);
        SERVER_MAP.put(ftpServer.getKey(), ftpServer);
        ftpServer.start();
    }

}