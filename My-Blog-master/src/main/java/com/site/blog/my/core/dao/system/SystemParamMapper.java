package com.site.blog.my.core.dao.system;

import com.site.blog.my.core.entity.system.SystemParam;
import org.apache.ibatis.annotations.Param;

public interface SystemParamMapper {
    SystemParam findByName(@Param("name")String name);
}
