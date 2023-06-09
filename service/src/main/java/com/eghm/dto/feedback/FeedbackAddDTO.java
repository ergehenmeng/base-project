package com.eghm.dto.feedback;

import com.eghm.annotation.Padding;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 手机反馈新增
 * @author 二哥很猛
 * @date 2019/8/28 10:47
 */
@Data
@ApiModel
public class FeedbackAddDTO implements Serializable {

    private static final long serialVersionUID = -1472450823258477249L;

    @ApiModelProperty(value = "反馈内容分类",required = true)
    private Integer classify;

    @ApiModelProperty(value = "反馈内容",required = true)
    private String content;

    @ApiModelProperty(value = "用户id", hidden = true)
    @Padding
    private Long memberId;

    @ApiModelProperty(value = "设备品牌", hidden = true)
    @Padding
    private String deviceBrand;

    @ApiModelProperty(value = "设备型号", hidden = true)
    @Padding
    private String deviceModel;

    @ApiModelProperty(value = "软件版本", hidden = true)
    @Padding
    private String version;

    @ApiModelProperty(value = "系统版本", hidden = true)
    @Padding
    private String systemVersion;

}
