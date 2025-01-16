package com.wangtao.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangtao.model.system.SysOperLog;
import com.wangtao.model.vo.SysOperLogQueryVo;

public interface SysOperLogService extends IService<SysOperLog> {
    Page findPage(Page page, SysOperLogQueryVo sysOperLogQueryVo);
}
