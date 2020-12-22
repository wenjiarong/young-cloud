package org.spring.file.test.tftp;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springyoung.file.packet.TftpErrorPacket;
import org.springyoung.file.packet.enums.TftpError;

@Slf4j
public class TftpErrorPacketTest {

    @Test
    public void t1() {
        TftpErrorPacket packet1 = new TftpErrorPacket(TftpError.FILE_EXISTS);
        ByteBuf byteBuf = packet1.toByteBuf();
		log.info("packet1 = " + packet1);

        // 用byteBuf构建，看能否还原
        TftpErrorPacket packet2 = new TftpErrorPacket(byteBuf);
		log.info("packet2 = " + packet2);
        Assert.assertEquals(packet1.getOpcode(), packet2.getOpcode());
        Assert.assertEquals(packet1.getErrorCode(), packet1.getErrorCode());
        Assert.assertEquals(packet1.getErrorMessage(), packet2.getErrorMessage());
    }
}
