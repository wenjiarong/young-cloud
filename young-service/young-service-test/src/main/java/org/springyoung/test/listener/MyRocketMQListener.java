package org.springyoung.test.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springyoung.test.constant.RocketMqConstant;
import org.springyoung.test.entity.TradeLog;
import org.springyoung.test.service.ITradeLogService;

@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = RocketMqConstant.PAY_CONSUMER_GROUP, topic = RocketMqConstant.PAY_TOPIC)
public class MyRocketMQListener implements RocketMQListener<TradeLog> {

    @Autowired
    private ITradeLogService tradeLogService;

    @Override
    public void onMessage(TradeLog tradeLog) {
        log.info("监听到用户已经下单并支付成功ID为{}，名称为{}的商品", tradeLog.getGoodsId(), tradeLog.getGoodsName());
        this.tradeLogService.packageAndSend(tradeLog);
    }

}