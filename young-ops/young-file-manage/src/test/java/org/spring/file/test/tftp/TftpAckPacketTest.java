package org.spring.file.test.tftp;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springyoung.file.packet.TftpAckPacket;

@Slf4j
public class TftpAckPacketTest {

    @Test
    public void t1() {
        TftpAckPacket packet1 = new TftpAckPacket(110);
        ByteBuf byteBuf = packet1.toByteBuf();
        log.info("packet1 = " + packet1);

        // 用byteBuf构建，看能否还原
        TftpAckPacket packet2 = new TftpAckPacket(byteBuf);
        log.info("packet2 = " + packet2);
        Assert.assertEquals(packet1.getOpcode(), packet2.getOpcode());
        Assert.assertEquals(packet1.getBlockNumber(), packet2.getBlockNumber());

    }

}
