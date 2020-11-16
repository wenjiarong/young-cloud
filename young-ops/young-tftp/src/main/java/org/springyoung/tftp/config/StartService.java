package org.springyoung.tftp.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springyoung.tftp.server.TftpServer;

import java.io.File;

/**
 * 继承Application接口后项目启动时会按照执行顺序执行run方法
 * 通过设置Order的value来指定执行的顺序
 */
@Component
@Order(value = 1)
public class StartService implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        File rootDir = new File("young-ops/young-tftp/src/main/resources/files");
        TftpServer server = new TftpServer(rootDir);
        // 高级的配置
        // 允许读取
        //server.setAllowRead(true);
        // 不允许写入
        //server.setAllowWrite(false);
        // 不允许覆盖
        //server.setAllowOverwrite(false);
        server.start();
    }

}