package org.springyoung.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springyoung.test.entity.TradeLog;

public interface ITradeLogService extends IService<TradeLog> {

    void packageAndSend(TradeLog tradeLog);

}