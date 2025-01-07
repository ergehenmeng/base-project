package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("help_center")
@EqualsAndHashCode(callSuper = true)
public class HelpCenter extends BaseEntity {

    @Schema(description = "帮助分类取sys_dict表中help_type字段")
    private Integer helpType;

    @Schema(description = "状态 0:不显示 1:显示")
    private Integer state;

    @Schema(description = "问")
    private String ask;

    @Schema(description = "答")
    private String answer;

    @Schema(description = "排序(小<->大)")
    private Integer sort;

}