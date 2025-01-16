package com.wangtao.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangtao.model.system.SysOperLog;
import com.wangtao.model.vo.SysOperLogQueryVo;
import com.wangtao.system.mapper.SysOperLogMapper;
import com.wangtao.system.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {

    @Autowired
    SysOperLogMapper sysOperLogMapper;

    @Override
    public Page findPage(Page page, SysOperLogQueryVo sysOperLogQueryVo) {
        return sysOperLogMapper.findPage(page,sysOperLogQueryVo);
    }
}
