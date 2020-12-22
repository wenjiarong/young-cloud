package org.springyoung.file.packet;

import io.netty.buffer.ByteBuf;
import org.springyoung.file.packet.enums.TftpOpcode;

/**
 * @ClassName TftpReadRequestPacket
 * @Description TODO
 * @Author 小温
 * @Date 2020/11/12 9:41
 * @Version 1.0
 */
public class TftpReadRequestPacket extends TftpRequestPacket {


    /**
     * @param filename
     * @param blockSize
     * @param timeout
     * @param transferSize
     */
    public TftpReadRequestPacket(String filename, Integer blockSize, Integer timeout,
                                 Long transferSize) {
        super(TftpOpcode.RRQ);
        this.filename = filename;
        this.blockSize = blockSize;
        this.timeout = timeout;
        this.transferSize = transferSize;
        this.mode = TftpRequestPacket.MODE_OCTET;
    }

    /**
     * @param byteBuf
     */
    public TftpReadRequestPacket(ByteBuf byteBuf) {
        super(byteBuf);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TftpReadRequestPacket{");
        sb.append("opcode=").append(opcode);
        sb.append(", mode='").append(mode).append('\'');
        sb.append(", filename='").append(filename).append('\'');
        sb.append(", blockSize=").append(blockSize);
        sb.append(", timeout=").append(timeout);
        sb.append(", transferSize=").append(transferSize);
        sb.append('}');
        return sb.toString();
    }
}
