package com.eghm.dto.feedback;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 手机反馈新增
 * @author 二哥很猛
 * @date 2019/8/28 10:47
 */
@Data
public class FeedbackAddDTO {

    @ApiModelProperty(value = "反馈内容分类",required = true)
    private Integer classify;

    @ApiModelProperty(value = "反馈内容",required = true)
    @WordChecker
    private String content;

    @ApiModelProperty(value = "用户id", hidden = true)
    @Assign
    private Long memberId;

    @ApiModelProperty(value = "设备品牌", hidden = true)
    @Assign
    private String deviceBrand;

    @ApiModelProperty(value = "设备型号", hidden = true)
    @Assign
    private String deviceModel;

    @ApiModelProperty(value = "软件版本", hidden = true)
    @Assign
    private String version;

    @ApiModelProperty(value = "系统版本", hidden = true)
    @Assign
    private String systemVersion;

}
