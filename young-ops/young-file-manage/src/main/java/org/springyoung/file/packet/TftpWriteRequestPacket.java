package org.springyoung.file.packet;

import io.netty.buffer.ByteBuf;
import org.springyoung.file.packet.enums.TftpOpcode;

/**
 * @ClassName TftpWriteRequestPacket
 * @Description TODO
 * @Author 小温
 * @Date 2020/11/12 9:41
 * @Version 1.0
 */
public class TftpWriteRequestPacket extends TftpRequestPacket {


    /**
     * @param filename
     * @param blockSize
     * @param timeout
     * @param transferSize
     */
    public TftpWriteRequestPacket(String filename, Integer blockSize, Integer timeout,
                                  Long transferSize) {
        super(TftpOpcode.WRQ);
        this.filename = filename;
        this.blockSize = blockSize;
        this.timeout = timeout;
        this.transferSize = transferSize;
        this.mode = TftpRequestPacket.MODE_OCTET;
    }

    /**
     * @param byteBuf
     */
    public TftpWriteRequestPacket(ByteBuf byteBuf) {
        super(byteBuf);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TftpWriteRequestPacket{");
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
