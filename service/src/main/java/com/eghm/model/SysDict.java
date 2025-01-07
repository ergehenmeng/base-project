package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("sys_dict")
@EqualsAndHashCode(callSuper = true)
public class SysDict extends BaseEntity {

    @Schema(description = "字典中文名称")
    private String title;

    @Schema(description = "字典编码")
    private String nid;

    @Schema(description = "字典分类: 1: 系统字典 2: 业务字典")
    private Integer dictType;

    @Schema(description = "备注信息")
    private String remark;

}