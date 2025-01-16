package com.wangtao.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangtao.model.system.SysMenu;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    Integer getCountByParentId(Long id);

    List<SysMenu> getMenuListByUserId(Long userId);
}
