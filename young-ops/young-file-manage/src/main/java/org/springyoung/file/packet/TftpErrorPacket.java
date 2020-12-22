package org.springyoung.file.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springyoung.file.packet.enums.TftpError;
import org.springyoung.file.packet.enums.TftpOpcode;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName TftpErrorPacket
 * @Description TODO
 * @Author 小温
 * @Date 2020/11/12 9:41
 * @Version 1.0
 */
public class TftpErrorPacket extends BaseTftpPacket {

    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 错误消息
     */
    private String errorMessage;

    /**
     * @param tftpError
     */
    public TftpErrorPacket(TftpError tftpError) {
        super(TftpOpcode.ERROR);
        this.errorCode = tftpError.getErrorCode();
        this.errorMessage = tftpError.getErrorMessage();
    }

    /**
     * @param byteBuf
     */
    public TftpErrorPacket(ByteBuf byteBuf) {
        super(byteBuf);
        this.opcode = TftpOpcode.get(byteBuf.readUnsignedShort());
        this.errorCode = byteBuf.readUnsignedShort();
        if (byteBuf.isReadable() && byteBuf.readableBytes() > 1) {
            byte[] bytes = new byte[byteBuf.readableBytes() - 1];
            byteBuf.readBytes(bytes);
            this.errorMessage = new String(bytes, StandardCharsets.US_ASCII);
        }
    }

    @Override
    public ByteBuf toByteBuf() {
        ByteBuf byteBuf = Unpooled.buffer(30);
        byteBuf.writeBytes(this.opcode.toByteArray());
        byteBuf.writeShort(this.errorCode);
        byteBuf.writeBytes(this.errorMessage.getBytes(StandardCharsets.US_ASCII));
        byteBuf.writeByte(0);
        return byteBuf;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TftpErrorPacket{");
        sb.append("opcode=").append(opcode);
        sb.append(", errorCode=").append(errorCode);
        sb.append(", errorMessage='").append(errorMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
