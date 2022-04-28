package com.site.blog.my.core.entity.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 系统参数
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemParam {
    private Long id;
    /**参数名*/
    private String name;
    /**参数值*/
    private String value;
    /**类型*/
    private String type;
    /**描述备注*/
    private String desc;
    /**是否可用*/
    private Boolean valid;
    private Date createDate;
    private Date updateDate;
}
