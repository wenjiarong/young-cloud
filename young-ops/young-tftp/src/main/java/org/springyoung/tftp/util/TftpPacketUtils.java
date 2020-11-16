package org.springyoung.tftp.util;

import io.netty.buffer.ByteBuf;
import org.springyoung.tftp.packet.enums.TftpOpcode;
import org.springyoung.tftp.packet.*;

/**
 * @ClassName TftpPacketUtils
 * @Description TODO
 * @Author 小温
 * @Date 2020/11/12 9:41
 * @Version 1.0
 */
public class TftpPacketUtils {

    /**
     *
     * @param buf
     * @return
     */
    public static BaseTftpPacket create(ByteBuf buf) {
        TftpOpcode opcode = TftpOpcode.get(buf.readUnsignedShort());
        buf.readerIndex(buf.readerIndex() - 2);
        switch (opcode) {
            case RRQ:
                return new TftpReadRequestPacket(buf);
            case WRQ:
                return new TftpWriteRequestPacket(buf);
            case DATA:
                return new TftpDataPacket(buf);
            case ACK:
                return new TftpAckPacket(buf);
            case OACK:
                return new TftpOptionAckPacket(buf);
            case ERROR:
                return new TftpErrorPacket(buf);
            default:
                // nop 不会执行至这里
                return null;
        }

    }

}
