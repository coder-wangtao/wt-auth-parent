package com.wangtao.system.service.impl;

import com.wangtao.model.system.SysLoginLog;
import com.wangtao.system.mapper.SysLoginLogMapper;
import com.wangtao.system.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginLogServiceImpl implements LoginLogService {
    @Autowired
    SysLoginLogMapper loginLogMapper;

    @Override
    public void saveLoginLog(SysLoginLog sysLoginLog) {
        loginLogMapper.insert(sysLoginLog);
    }
}
