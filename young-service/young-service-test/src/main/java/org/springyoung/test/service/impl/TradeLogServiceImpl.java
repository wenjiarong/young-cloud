package org.springyoung.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springyoung.test.entity.TradeLog;
import org.springyoung.test.entity.TransactionLog;
import org.springyoung.test.mapper.TradeLogMapper;
import org.springyoung.test.mapper.TransactionLogMapper;
import org.springyoung.test.service.ITradeLogService;

import java.util.Date;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class TradeLogServiceImpl extends ServiceImpl<TradeLogMapper, TradeLog> implements ITradeLogService {

    private final RocketMQTemplate rocketMQTemplate;
    private final TransactionLogMapper transactionLogMapper;

    /*@Override
    @Transactional
    public boolean orderAndPay(TradeLog tradeLog) {
        tradeLog.setCreateTime(new Date());
        tradeLog.setStatus("下单并支付成功");

        // 保存支付日志
        this.save(tradeLog);
        log.info("用户已经下单并支付成功商品ID为{}，名称为{}的商品", tradeLog.getGoodsId(), tradeLog.getGoodsName());

        // 往RocketMQ发送支付成功消息
        // RocketMQTemplate的convertAndSend的第一个参数相当于topic，消息主题，第二个参数为payload，即消息内容。
        this.rocketMQTemplate.convertAndSend(RocketMqConstant.PAY_TOPIC, tradeLog);
        return true;
    }*/

    /**
     * 分布式事务--半消息  sendMessageInTransaction方法发送了一条半消息
     *
     * @param tradeLog
     * @return
     */
    @Override
    public boolean orderAndPay(TradeLog tradeLog) {
        log.info("检测商品Id为{}，名称为{}的商品库存，库存充足", tradeLog.getGoodsId(), tradeLog.getGoodsName());
        String transactionId = UUID.randomUUID().toString();
        // 往RocketMQ发送事务消息
        // this.rocketMQTemplate.convertAndSend("pay-success", tradeLog);
        this.rocketMQTemplate.sendMessageInTransaction(
                "pay-success-group", // 事务消息分组，组名
                "pay-success", // 事务消息topic
                MessageBuilder.withPayload(tradeLog)
                        .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                        .build(), // 消息
                tradeLog // 额外参数，供后续回调使用
        );
        return true;
    }


    @Override
    @Transactional
    public void pay(TradeLog tradeLog, String transactionId) {
        tradeLog.setCreateTime(new Date());
        tradeLog.setStatus("下单并支付成功");
        // 保存支付日志
        this.save(tradeLog);
        log.info("用户已经下单并支付成功商品ID为{}，名称为{}的商品", tradeLog.getGoodsId(), tradeLog.getGoodsName());
        // 记录事务日志
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setTransactionId(transactionId);
        String remark = String.format("事务ID为%s的本地事务执行成功", transactionId);
        transactionLog.setRemark(remark);
        transactionLogMapper.insert(transactionLog);
        log.info("事务ID为{}的本地事务执行成功", transactionId);
    }

    @Override
    @Transactional
    public void packageAndSend(TradeLog tradeLog) {
        TradeLog tl = new TradeLog();
        tl.setGoodsId(tradeLog.getGoodsId());
        tl.setGoodsName(tradeLog.getGoodsName());
        tl.setStatus("打包完毕，开始物流配送！");
        tl.setCreateTime(new Date());

        this.save(tl);
        log.info("商品ID为{}，名称为{}的商品打包完毕，开始物流配送", tradeLog.getGoodsId(), tradeLog.getGoodsName());
    }

}