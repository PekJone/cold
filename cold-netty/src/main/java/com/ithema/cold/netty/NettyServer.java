package com.ithema.cold.netty;

import com.ithema.cold.netty.service.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-18  10:08
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NettyServer implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(NettyServer.class,args);
    }


    @Override
    public void run(String... args) throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        bootstrap.group(boss,worker);
        bootstrap.option(ChannelOption.SO_BACKLOG,128);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);

        bootstrap.channel(NioServerSocketChannel.class);

        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new ServerHandler());
            }
        });
        try{
            ChannelFuture f = bootstrap.bind(10010).sync();
            f.channel().closeFuture().sync();
        }finally{
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
