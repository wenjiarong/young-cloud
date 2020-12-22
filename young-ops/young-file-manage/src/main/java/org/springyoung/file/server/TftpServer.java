package org.springyoung.file.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ResourceLeakDetector;
import org.springyoung.file.channel.TftpServerChannel;
import org.springyoung.file.channel.TftpServerChildChannel;
import org.springyoung.file.handler.TftpServerHandler;

import java.io.File;

/**
 * @ClassName TftpServer
 * @Description TODO
 * @Author 小温
 * @Date 2020/11/12 9:41
 * @Version 1.0
 */
public class TftpServer {

    /**
     * 唯一key
     */
    protected String key;

    /**
     * 文件目录
     */
    protected File rootDir;

    /**
     * 是否允许读取
     */
    protected boolean allowRead;

    /**
     * 是否允许写入
     */
    protected boolean allowWrite;
    /**
     * 是否允许覆盖文件
     */
    protected boolean allowOverwrite;

    /**
     * 传输data报文失败时的重试次数
     */
    protected int maxRetries;

    /**
     * 传输data报文失败次数
     */
    protected int maxError;


    /**
     * 端口
     */
    private int port;

    private Channel serverChannel;

    private ServerBootstrap bootstrap;

    private EventLoopGroup group;


    /**
     * 使用默认端口来构建。tftp的默认端口为69
     */
    public TftpServer(File rootDir, String key) {
        this(rootDir, 69);
        this.key = key;
    }

    public TftpServer(File rootDir, int port) {
        this.rootDir = rootDir;
        this.port = port;
        //
        this.allowRead = true;
        this.allowWrite = true;
        this.allowOverwrite = true;
        this.maxRetries = 2;
        this.maxError = 5;
        //
        group = new NioEventLoopGroup(5);
        bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                // 数据报channel
                .channel(TftpServerChannel.class)
                // 配置接收缓冲区, 8KB
                .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(8192 + 4))
                .childHandler(new ChannelInitializer<TftpServerChildChannel>() {
                    @Override
                    protected void initChannel(TftpServerChildChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast(new TftpServerHandler(TftpServer.this));
                    }
                });
        // 内存泄露检查
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
    }


    /**
     * 启动服务器
     */
    public void start() throws Exception {
        serverChannel = bootstrap.bind(port)
                .sync().channel();
        //
        serverChannel.closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                group.shutdownGracefully();
            }
        });
    }


    /**
     * 停止服务器
     */
    public void stop() {
        serverChannel.close();
    }

    public String getKey() {
        return key;
    }

    public File getRootDir() {
        return rootDir;
    }

    public void setRootDir(File rootDir) {
        this.rootDir = rootDir;
    }

    public boolean isAllowRead() {
        return allowRead;
    }

    public void setAllowRead(boolean allowRead) {
        this.allowRead = allowRead;
    }

    public boolean isAllowWrite() {
        return allowWrite;
    }

    public void setAllowWrite(boolean allowWrite) {
        this.allowWrite = allowWrite;
    }

    public boolean isAllowOverwrite() {
        return allowOverwrite;
    }

    public void setAllowOverwrite(boolean allowOverwrite) {
        this.allowOverwrite = allowOverwrite;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public int getMaxError() {
        return maxError;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TftpServer{");
        sb.append("port=").append(port);
        sb.append(", rootDir=").append(rootDir);
        sb.append(", allowRead=").append(allowRead);
        sb.append(", allowWrite=").append(allowWrite);
        sb.append(", allowOverwrite=").append(allowOverwrite);
        sb.append(", maxRetries=").append(maxRetries);
        sb.append('}');
        return sb.toString();
    }

}
