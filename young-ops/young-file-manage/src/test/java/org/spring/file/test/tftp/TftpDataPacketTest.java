package org.spring.file.test.tftp;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springyoung.file.packet.TftpDataPacket;

@Slf4j
public class TftpDataPacketTest {

    @Test
    public void t1() {
        TftpDataPacket packet1 = new TftpDataPacket(10, new byte[]{1, 2, 3, 4, 5});
        ByteBuf byteBuf = packet1.toByteBuf();
		log.info("packet1 = " + packet1);

        // 用byteBuf构建，看能否还原
        TftpDataPacket packet2 = new TftpDataPacket(byteBuf);
        log.info("packet2 = " + packet2);
        Assert.assertEquals(packet1.getOpcode(), packet2.getOpcode());
        Assert.assertEquals(packet1.getBlockNumber(), packet2.getBlockNumber());
        Assert.assertEquals(packet1.getBlockData().length, packet2.getBlockData().length);

    }

}
