package com.eghm.model.dto.dict;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/1/14 11:50
 */
@Data
public class DictEditRequest implements Serializable {

    private static final long serialVersionUID = 2506674696822623145L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "数据字典名称", required = true)
    @NotNull(message = "名称不能为空")
    private String title;

    /**
     * 字典隐藏值
     */
    @ApiModelProperty(value = "数据字典隐藏值", required = true)
    @NotNull(message = "名称不能为空")
    private Integer hiddenValue;

    /**
     * 显示值
     */
    private String  showValue;

    /**
     * 锁定状态(禁止编辑):0:未锁定 1:锁定
     */
    private Boolean locked;



    /**
     * 备注信息
     */
    private String remark;
}
