package org.springyoung.tftp.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springyoung.tftp.packet.enums.TftpOpcode;

/**
 * @ClassName TftpAckPacket
 * @Description TODO
 * @Author 小温
 * @Date 2020/11/12 9:41
 * @Version 1.0
 */
public class TftpAckPacket extends BaseTftpPacket {

    /**
     * 0-65535
     */
    private int blockNumber;

    /**
     * @param blockNumber
     */
    public TftpAckPacket(int blockNumber) {
        super(TftpOpcode.ACK);
        this.blockNumber = blockNumber;
    }


    /**
     * @param byteBuf
     */
    public TftpAckPacket(ByteBuf byteBuf) {
        super(byteBuf);
        this.opcode = TftpOpcode.get(byteBuf.readUnsignedShort());
        this.blockNumber = byteBuf.readUnsignedShort();
    }


    @Override
    public ByteBuf toByteBuf() {
        ByteBuf byteBuf = Unpooled.buffer(4);
        byteBuf.writeBytes(this.opcode.toByteArray());
        byteBuf.writeShort(blockNumber);
        return byteBuf;
    }


    public int getBlockNumber() {
        return blockNumber;
    }


    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TftpAckPacket{");
        sb.append("opcode=").append(opcode);
        sb.append(", blockNumber=").append(blockNumber);
        sb.append('}');
        return sb.toString();
    }

}
