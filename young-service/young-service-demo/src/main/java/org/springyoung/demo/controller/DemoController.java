package org.springyoung.demo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springyoung.core.response.R;
import org.springyoung.demo.service.ITradeLogService;
import org.springyoung.test.entity.TradeLog;

@RestController
@AllArgsConstructor
@Slf4j
public class DemoController {

    private final ITradeLogService tradeLogService;

    @GetMapping("/pay")
    public R orderAndPay(TradeLog tradeLog) {
        return R.data(this.tradeLogService.orderAndPay(tradeLog));
    }

}