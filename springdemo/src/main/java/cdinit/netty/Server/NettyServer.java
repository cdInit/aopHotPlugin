package cdinit.netty.Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup boos = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
                .group(boos, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("stringEncoder",new StringEncoder(CharsetUtil.UTF_8))
                                .addLast("frameDecoder",
                                        new MsgLengthFieldBasedFrameDecoder(1024*1024, 0,
                                                8, 0, 8))
                                .addLast("stringDecoder", new StringDecoder(Charset.forName("UTF8")))
                                .addLast("messageHandler", new ServerHandler())
                                ;

                    }
                })
                .bind(9999);
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
    public MsgLengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    @Override
    protected long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
        if(length == 8){
            buf = buf.order(order);
            byte[] lengthBytes = new byte[8];
            buf.readBytes(lengthBytes);
            buf.resetReaderIndex();
            return Integer.valueOf(new String(lengthBytes));
        } else {
            return super.getUnadjustedFrameLength(buf, offset, length, order);
        }
    }

}
