package cdinit.netty.Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class NettySocketServer {

    private final String CHARSET_NAME = "GBK";

    private final int bosscount = 2;

    private final int workerCount = 8;

    private final int tcpPort = 8888;

    private final int backlog = 100;

    private final int receiveBufferSize = 1048576;

    private ServerBootstrap serverBootstrap;

    private ChannelFuture serverChannelFuture;

    public NamedThreadFactory bossThreadFactory() {

        return new NamedThreadFactory("Server-Worker");

    }

    public NioEventLoopGroup bossGroup() {

        return new NioEventLoopGroup(bosscount, bossThreadFactory());

    }

    public NamedThreadFactory workerThreadFactory() {

        return new NamedThreadFactory("Server-Worker");

    }

    public NioEventLoopGroup workerGroup() {

        return new NioEventLoopGroup(workerCount, workerThreadFactory());

    }

    public ServerBootstrap bootstrap() {

        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup(), workerGroup()).channel(NioServerSocketChannel.class)

                .option(ChannelOption.SO_BACKLOG, backlog)

                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override

                    protected void initChannel(SocketChannel ch) throws Exception {

                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast("logging", new LoggingHandler(LogLevel.ERROR))

                                .addLast("stringEncoder", new StringEncoder(Charset.forName("GBK")))

                                .addLast("frameDecoder", new MsgLengthFieldBasedFrameDecoder(receiveBufferSize, 0, 6, 0, 6))

                                .addLast("stringDecoder", new StringDecoder(Charset.forName("GBK")))

                                .addLast("messageHandler", new ServerMessageHandler());

                    }

                });

        return bootstrap;

    }

    @PostConstruct

    public void start() throws Exception {

        serverBootstrap = bootstrap();

        serverChannelFuture = serverBootstrap.bind(tcpPort).sync();

        log.info("Starting server at tcpPort {}" , tcpPort);

    }

    @PreDestroy

    public void stop() throws Exception {

        serverChannelFuture.channel().closeFuture().sync();

    }

    static class NamedThreadFactory implements ThreadFactory {

        public static AtomicInteger counter = new AtomicInteger(1);

        private String name = this.getClass().getName();

        private boolean deamon ;//守护线程

        private int priority ; //线程优先级

        public NamedThreadFactory(String name){

            this(name, false);

        }

        public NamedThreadFactory(String name,boolean deamon){

            this(name, deamon, -1);

        }

        public NamedThreadFactory(String name,boolean deamon,int priority){

            this.name = name ;

            this.deamon = deamon ;

            this.priority = priority ;

        }

        @Override

        public Thread newThread(Runnable r) {

            Thread thread = new Thread(r,name+"["+counter.getAndIncrement()+"]");

            thread.setDaemon(deamon);

            if(priority != -1){

                thread.setPriority(priority);

            }

            return thread;

        }

    }

//拆包

    class MsgLengthFieldBasedFrameDecoder extends LengthFieldBasedFrameDecoder {

        /**

         * @param maxFrameLength 解码时，处理每个帧数据的最大长度

         * @param lengthFieldOffset 该帧数据中，存放该帧数据的长度的数据的起始位置

         * @param lengthFieldLength 记录该帧数据长度的字段本身的长度

         * @param lengthAdjustment 修改帧数据长度字段中定义的值，可以为负数

         * @param initialBytesToStrip 解析的时候需要跳过的字节数

         */

        public MsgLengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,

                                               int lengthAdjustment, int initialBytesToStrip) {

            super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);

        }

        @Override

        protected long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {

            if(length == 6){

                buf = buf.order(order);

                byte[] lengthBytes = new byte[6];

                buf.readBytes(lengthBytes);

                buf.resetReaderIndex();

                return Integer.valueOf(new String(lengthBytes));

            } else {

                return super.getUnadjustedFrameLength(buf, offset, length, order);

            }

        }

    }

    class ServerMessageHandler extends ChannelInboundHandlerAdapter {

        /**

         * 功能：读取服务器发送过来的信息

         */

        @Override

        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

            if (msg instanceof String) {

                try {

                    doBusinessLogic(ctx,(String)msg);

                } finally {

                    ReferenceCountUtil.release(msg);

                }

            }

        }

// 处理业务逻辑

        private void doBusinessLogic(ChannelHandlerContext ctx,String msg) throws Exception {

// todo Business Logic

            msg = formatMsg(msg);

            ctx.channel().writeAndFlush(msg).addListener(ChannelFutureListener.CLOSE);

        }

        private String formatMsg(String msg) {

            byte[] bodyBytes = msg.getBytes(Charset.forName(CHARSET_NAME));

            int bodyLength = bodyBytes.length;

            NumberFormat numberFormat = NumberFormat.getNumberInstance();

            numberFormat.setMinimumIntegerDigits(6);

            numberFormat.setGroupingUsed(false);

            return numberFormat.format(bodyLength) + msg;

        }

        @Override

        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

            ctx.close();

        }

    }

    public static void main(String[] args) throws Exception{

        NettySocketServer server = new NettySocketServer();

        server.start();

    }

}
