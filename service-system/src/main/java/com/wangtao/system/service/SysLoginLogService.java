package com.wangtao.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangtao.model.system.SysLoginLog;
import com.wangtao.model.vo.SysLoginLogQueryVo;

public interface SysLoginLogService extends IService<SysLoginLog> {
    Page findPage(Page page, SysLoginLogQueryVo sysLoginLogQueryVo);
}
