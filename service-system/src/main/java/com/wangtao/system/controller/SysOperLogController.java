package com.wangtao.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangtao.common.result.Result;
import com.wangtao.model.system.SysOperLog;
import com.wangtao.model.vo.SysOperLogQueryVo;
import com.wangtao.system.service.SysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "操作日志控制器")
@RestController
@RequestMapping("/admin/system/sysOperLog")
public class SysOperLogController {

    @Autowired
    SysOperLogService sysOperLogService;

    @ApiOperation("分页查询")
    @GetMapping("/{pageNum}/{pageSize}")
    public Result index(@PathVariable Long pageNum, @PathVariable Long pageSize, SysOperLogQueryVo sysOperLogQueryVo) {
        Page page = new Page(pageNum, pageSize);
        Page sysOperLogPage = sysOperLogService.findPage(page,sysOperLogQueryVo);
       return Result.ok(sysOperLogPage);
    }

    @ApiOperation("操作日志的详情信息")
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Long id) {
        SysOperLog sysOperLog = sysOperLogService.getById(id);
        return Result.ok(sysOperLog);
    }
}
