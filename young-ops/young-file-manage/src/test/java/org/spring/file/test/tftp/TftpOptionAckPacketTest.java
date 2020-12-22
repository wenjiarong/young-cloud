package org.spring.file.test.tftp;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springyoung.file.packet.TftpOptionAckPacket;

@Slf4j
public class TftpOptionAckPacketTest {

    @Test
    public void t1() {
        TftpOptionAckPacket packet1 = new TftpOptionAckPacket(1024, 5, 100_1024L);
        ByteBuf byteBuf = packet1.toByteBuf();
		log.info("packet1 = " + packet1);

        // 用byteBuf构建，看能否还原
        TftpOptionAckPacket packet2 = new TftpOptionAckPacket(byteBuf);
		log.info("packet2 = " + packet2);
        Assert.assertEquals(packet1.getOpcode(), packet2.getOpcode());
        Assert.assertEquals(packet1.getBlockSize(), packet2.getBlockSize());
        Assert.assertEquals(packet1.getTimeout(), packet2.getTimeout());
        Assert.assertEquals(packet1.getTransferSize(), packet2.getTransferSize());
    }


}
