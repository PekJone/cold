package com.ithema.cold.netty.service;

import com.alibaba.fastjson.JSON;
import com.ithema.cold.common.netty.MessageEntity;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-18  10:24
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {
    public  final static String TOPIC= "wang";
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        log.info("ServerHandler.channelRead()");
        ByteBuf in = (ByteBuf) o;
        try{
            if(in.readableBytes()<=0){
                return;
            }
            byte[] req = new byte[in.readableBytes()];
            in.readBytes(req);
            String body = new String(req,"UTF-8");
            log.info("报文内容为：{}",body);
            MessageEntity message = this.parseMessage(body);
            String json = JSON.toJSONString(message);
            log.info("增加报文仪表号：{}内容：{}",message.getMetercode(),body);
            String res = "SHT|true|\0";
            ByteBuf buf = channelHandlerContext.alloc().buffer();
            if(res.length()>0){
                buf.writeBytes(res.getBytes());
                channelHandlerContext.writeAndFlush(buf);
                if(null!=buf){
                    buf.resetWriterIndex();
                }
            }
        }catch (Exception e){
             e.printStackTrace();
             log.error(e.getMessage());
        }finally {
            ReferenceCountUtil.release(o);
        }
        if(null!=in){
            in.resetWriterIndex();
        }
    }

    private MessageEntity parseMessage(String body) {
        String[] arrBody = body.split("\\|");
        log.info("报文字段数量:{}", arrBody.length );

        MessageEntity msg = new MessageEntity();
        msg.setMetercode(arrBody[1]);
        msg.setTem(Integer.valueOf(arrBody[2]));
        msg.setHum(Integer.valueOf(arrBody[3]));
        msg.setState(Integer.valueOf(arrBody[4]));
        msg.setLon(arrBody[5]);
        msg.setLat(arrBody[6]);

        java.text.DateFormat format_date = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        String sysdate = format_date.format(new Date());
        msg.setCurtime(sysdate);

        return msg;
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
          channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
          channelHandlerContext.close();
    }
}
