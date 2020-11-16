package org.springyoung.tftp.packet;

import io.netty.buffer.ByteBuf;
import org.springyoung.tftp.packet.enums.TftpOpcode;

import java.net.InetSocketAddress;

/**
 * @ClassName BaseTftpPacket
 * @Description TODO
 * @Author 小温
 * @Date 2020/11/12 9:41
 * @Version 1.0
 */
public abstract class BaseTftpPacket {

    private static final int MIN_PACKET_SIZE = 4;

    protected TftpOpcode opcode;

    private InetSocketAddress remoteAddress;

    public BaseTftpPacket(TftpOpcode opcode) {
        this.opcode = opcode;
    }

    public BaseTftpPacket(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() < MIN_PACKET_SIZE) {
            throw new IllegalArgumentException("包长度不够");
        }
    }

    /**
     * 将实例转换成ByteBuf
     *
     * @return
     */
    public abstract ByteBuf toByteBuf();


    public TftpOpcode getOpcode() {
        return opcode;
    }

    public void setOpcode(TftpOpcode opcode) {
        this.opcode = opcode;
    }

    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

}
