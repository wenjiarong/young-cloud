package org.springyoung.file.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springyoung.file.packet.enums.TftpOpcode;

/**
 * @ClassName TftpDataPacket
 * @Description TODO
 * @Author 小温
 * @Date 2020/11/12 9:41
 * @Version 1.0
 */
public class TftpDataPacket extends BaseTftpPacket {

    /**
     * [0-65535]
     */
    private int blockNumber;

    /**
     * 块数据
     */
    private byte[] blockData;


    public TftpDataPacket(int blockNumber, byte[] blockData) {
        super(TftpOpcode.DATA);
        this.blockNumber = blockNumber;
        this.blockData = blockData;
    }


    /**
     * @param byteBuf
     */
    public TftpDataPacket(ByteBuf byteBuf) {
        super(byteBuf);
        this.opcode = TftpOpcode.get(byteBuf.readUnsignedShort());
        this.blockNumber = byteBuf.readUnsignedShort();
        if (byteBuf.readableBytes() > 0) {
            this.blockData = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(blockData);
        } else {
            blockData = new byte[0];
        }
    }


    @Override
    public ByteBuf toByteBuf() {
        ByteBuf byteBuf = Unpooled.buffer(2 + 2 + blockData.length);
        byteBuf.writeBytes(this.opcode.toByteArray());
        byteBuf.writeShort(blockNumber);
        if (blockData.length > 0) {
            byteBuf.writeBytes(blockData);
        }
        return byteBuf;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public byte[] getBlockData() {
        return blockData;
    }

    public void setBlockData(byte[] blockData) {
        this.blockData = blockData;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TftpDataPacket{");
        sb.append("opcode=").append(opcode);
        sb.append(", blockNumber=").append(blockNumber);
        sb.append(", blockData length=").append(blockData.length);
        sb.append('}');
        return sb.toString();
    }
}
