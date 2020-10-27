package org.springyoung.test.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springyoung.test.constant.RocketMqConstant;
import org.springyoung.test.entity.TradeLog;
import org.springyoung.test.entity.TransactionLog;
import org.springyoung.test.mapper.TransactionLogMapper;
import org.springyoung.test.service.ITradeLogService;

@Slf4j
@Component
@RocketMQTransactionListener(txProducerGroup = RocketMqConstant.PAY_PRODUCER_GROUP)
public class MyRocketMQLocalTransactionListener implements RocketMQLocalTransactionListener {

    @Autowired
    private ITradeLogService tradeLogService;
    @Autowired
    private TransactionLogMapper transactionLogMapper;

    /**
     * 执行本地事务
     *
     * @param message 消息
     * @param o       额外参数
     * @return RocketMQ事务状态
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        MessageHeaders headers = message.getHeaders();
        String transicationId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);

        try {
            TradeLog tradeLog = (TradeLog) o;
            this.tradeLogService.pay(tradeLog, transicationId); // 对应图中第3步，执行本地事务
            log.info("本地事务执行成功，往RocketMQ发送COMMIT");
            return RocketMQLocalTransactionState.COMMIT; // 对应图中第4步，COMMIT，半消息经过COMMIT后，消息消费端就可以消费这条消息了
        } catch (Exception e) {
            e.printStackTrace();
            log.info("本地事务回滚，往RocketMQ发送ROLLBACK", e);
            return RocketMQLocalTransactionState.ROLLBACK; // 对应途中第4步，ROLLBACK
        }
    }

    /**
     * RocketMQ回查本地事务状态，这个过程对应图中第5步
     *
     * @param message 消息
     * @return RocketMQ事务状态
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        MessageHeaders headers = message.getHeaders();
        String transicationId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        log.info("RocketMQ事务状态回查");
        // 从数据库中根据事务Id查询对应的事务日志，对应图中第6步
        TransactionLog transactionLog = transactionLogMapper.selectOne(
                new LambdaQueryWrapper<TransactionLog>().eq(TransactionLog::getTransactionId, transicationId)
        );
        // 对应图中的第7步骤
        return transactionLog != null ? RocketMQLocalTransactionState.COMMIT : RocketMQLocalTransactionState.ROLLBACK;
    }

}