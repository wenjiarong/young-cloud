package org.springyoung.tftp.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @ClassName BlockBufferTest
 * @Description TODO
 * @Author 小温
 * @Date 2020/11/23 10:51
 * @Version 1.0
 */
@Slf4j
public class BlockBufferTest {

    @Test
    public void t1() throws IOException {
        File file = new File("F:/home/YoungX/young-ops/young-tftp/src/main/resources/files/RG2000WN_v1.0.0.bin");
        RandomAccessFile raf;
        int blockSize = 128;
        byte[] blockBuffer;
        blockBuffer = new byte[blockSize];
        long fileLength = file.length();
        try {
            raf = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException exp) {
            return;
        }
        log.info("读请求, 文件：{} , 大小：{}B, 块大小：{}B, 分{}次传输.",
                file, fileLength, blockSize, (fileLength / blockSize) + 1);
        for (int i = 1; i <= 676; i++) {
            int readCount = raf.read(blockBuffer);
            System.out.println(readCount);
        }
    }

}
