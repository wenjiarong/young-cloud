package org.springyoung.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springyoung.common.utils.AddressUtil;
import org.springyoung.common.utils.YoungUtil;
import org.springyoung.core.mp.support.Condition;
import org.springyoung.core.mp.support.Query;
import org.springyoung.core.tool.utils.ObjectUtil;
import org.springyoung.system.entity.LoginLog;
import org.springyoung.system.entity.User;
import org.springyoung.system.mapper.LoginLogMapper;
import org.springyoung.system.service.ILoginLogService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

    @Override
    public IPage<LoginLog> findLoginLogs(LoginLog loginLog, Query query) {
        QueryWrapper<LoginLog> queryWrapper = new QueryWrapper<>();

        if (ObjectUtil.isNotEmpty(loginLog.getUserId())) {
            queryWrapper.lambda().eq(LoginLog::getUserId, loginLog.getUserId());
        }
        if (StringUtils.isNotBlank(loginLog.getLoginTimeFrom()) && StringUtils.isNotBlank(loginLog.getLoginTimeTo())) {
            queryWrapper.lambda()
                    .ge(LoginLog::getLoginTime, loginLog.getLoginTimeFrom())
                    .le(LoginLog::getLoginTime, loginLog.getLoginTimeTo());
        }

        return this.page(Condition.getPage(query), queryWrapper);
    }

    @Override
    public void saveLoginLog(LoginLog loginLog) {
        loginLog.setLoginTime(new Date());
        String ip = YoungUtil.getHttpServletRequestIpAddress();
        loginLog.setIp(ip);
        loginLog.setLocation(AddressUtil.getCityInfo(ip));
        this.save(loginLog);
    }

    @Override
    public void deleteLoginLogs(String[] ids) {
        List<String> list = Arrays.asList(ids);
        baseMapper.deleteBatchIds(list);
    }

    @Override
    public Long findTotalVisitCount() {
        return this.baseMapper.findTotalVisitCount();
    }

    @Override
    public Long findTodayVisitCount() {
        return this.baseMapper.findTodayVisitCount();
    }

    @Override
    public Long findTodayIp() {
        return this.baseMapper.findTodayIp();
    }

    @Override
    public List<Map<String, Object>> findLastTenDaysVisitCount(User user) {
        return this.baseMapper.findLastTenDaysVisitCount(user);
    }

    @Override
    public List<LoginLog> findUserLastSevenLoginLogs(Long userId) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(userId);

        Query query = new Query();
        query.setCurrent(1);
        // 近7日记录
        query.setSize(7);

        IPage<LoginLog> loginLogs = this.findLoginLogs(loginLog, query);
        return loginLogs.getRecords();
    }

}
