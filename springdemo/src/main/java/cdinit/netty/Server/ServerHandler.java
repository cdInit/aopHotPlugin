package cdinit.netty.Server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead接收-->" + msg);
        byte[] bytes = msg.toString().getBytes(CharsetUtil.UTF_8);
        int length = bytes.length;
        String res  = "00"+String.valueOf(length) + msg; // 模拟前8位 00103961﻿
        System.out.println("返回-->" + res);
        ctx.channel().writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}