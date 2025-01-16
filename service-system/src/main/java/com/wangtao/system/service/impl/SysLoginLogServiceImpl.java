package com.wangtao.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangtao.model.system.SysLoginLog;
import com.wangtao.model.vo.SysLoginLogQueryVo;
import com.wangtao.system.mapper.SysLoginLogMapper;
import com.wangtao.system.service.SysLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {
    @Autowired
    SysLoginLogMapper sysLoginLogMapper;

    @Override
    public Page findPage(Page page, SysLoginLogQueryVo sysLoginLogQueryVo) {
        return sysLoginLogMapper.findPage(page,sysLoginLogQueryVo);
    }
}
