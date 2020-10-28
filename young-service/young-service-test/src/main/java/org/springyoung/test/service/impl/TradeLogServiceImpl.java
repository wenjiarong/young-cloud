package org.springyoung.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springyoung.test.entity.TradeLog;
import org.springyoung.test.mapper.TradeLogMapper;
import org.springyoung.test.service.ITradeLogService;

import java.util.Date;

@Slf4j
@AllArgsConstructor
@Service
public class TradeLogServiceImpl extends ServiceImpl<TradeLogMapper, TradeLog> implements ITradeLogService {

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