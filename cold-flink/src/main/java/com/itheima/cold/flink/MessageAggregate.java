package com.itheima.cold.flink;

import akka.io.Tcp;
import com.ithema.cold.common.admin.entity.MeterEntity;
import com.ithema.cold.common.netty.MessageEntity;
import com.ithema.cold.common.utils.JedisUtil;
import com.ithema.cold.common.utils.SerializeUtil;
import org.apache.flink.api.common.functions.MapFunction;
import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-22  9:05
 */
public class MessageAggregate implements MapFunction<MessageEntity, MessageEntity> {
    final JedisUtil jedisUtil = JedisUtil.getInstance();

    @Override
    public MessageEntity map(MessageEntity value) throws Exception {
        Jedis jedis = jedisUtil.getJedis();
        try{
            byte[] meterBy = jedis.get(SafeEncoder.encode(value.getMetercode()));

            MeterEntity meter = (MeterEntity) SerializeUtil.unserialize(meterBy);

            value.setMeterid(meter.getId());
            value.setMetername(meter.getMetername());
            value.setHostcode(meter.getHostcode());
            value.setHostid(meter.getHostid());
            value.setHostname(meter.getHostname());

            value.setHouseid(meter.getHouseid());
            value.setHousecode(meter.getHousecode());
            value.setHousename(meter.getHousename());
            value.setCompanyid(meter.getCompanyid());
            value.setCompanyname(meter.getCompanyname());
//                value.setState(Integer.valueOf(meter.getMeterstatus()));

            value.setMaxtem(Integer.valueOf(meter.getMaxtem()));
            value.setMintem(Integer.valueOf(meter.getMintem()));
            value.setMaxhum(Integer.valueOf(meter.getMaxhum()));
            value.setMinhum(Integer.valueOf(meter.getMinhum()));

            if(value.getTem() > value.getMaxhum()){
                value.setTemalert(1);   //高温
            }
            else if(value.getTem() < value.getMintem()){
                value.setTemalert(-1);  //低温
            }
            else{
                value.setTemalert(0);   //正常
            }

            if(value.getHum() > value.getMaxhum()){
                value.setHumalert(1);
            }
            else if(value.getHum() < value.getMinhum()){
                value.setHumalert(-1);
            }
            else{
                value.setHumalert(0);
            }

            return value;
        }
        catch(Exception e){
            jedisUtil.returnBrokenResource(jedis);

            e.printStackTrace();
        }
        finally {
            jedisUtil.returnJedis(jedis);
        }

        return value;
    }
}
