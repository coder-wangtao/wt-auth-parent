package com.wangtao.system.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangtao.model.system.SysOperLog;
import com.wangtao.model.vo.SysOperLogQueryVo;
import org.apache.ibatis.annotations.Param;

public interface SysOperLogMapper extends BaseMapper<SysOperLog> {
    Page<SysOperLog> findPage(Page page, @Param("vo") SysOperLogQueryVo sysOperLogQueryVo);
}
