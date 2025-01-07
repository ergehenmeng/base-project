package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("sys_dict_item")
@EqualsAndHashCode(callSuper = true)
public class SysDictItem extends BaseEntity {

    @Schema(description = "字典编码")
    private String nid;

    @Schema(description = "数据字典隐藏值")
    private Integer hiddenValue;

    @Schema(description = "显示值")
    private String showValue;
}