package org.springyoung.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springyoung.test.entity.TradeLog;

public interface ITradeLogService extends IService<TradeLog> {

    boolean orderAndPay(TradeLog tradeLog);

    void pay(TradeLog tradeLog, String transactionId);

}