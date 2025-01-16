package com.wangtao.system.service.impl;

import com.wangtao.model.system.SysOperLog;
import com.wangtao.system.mapper.SysOperLogMapper;
import com.wangtao.system.service.OperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OperLogServiceImpl implements OperLogService {
    @Autowired
    SysOperLogMapper sysOperLogMapper;

    @Override
    public void saveOperLog(SysOperLog sysOperLog) {
        sysOperLogMapper.insert(sysOperLog);
    }
}
