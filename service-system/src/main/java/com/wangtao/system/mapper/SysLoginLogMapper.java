package com.wangtao.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangtao.model.system.SysLoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangtao.model.vo.SysLoginLogQueryVo;
import org.apache.ibatis.annotations.Param;

public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {
    Page<SysLoginLog> findPage(Page page, @Param("vo") SysLoginLogQueryVo sysLoginLogQueryVo);
}
