package com.eghm.model.dto.feedback;

import com.eghm.model.annotation.Sign;
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

    /**
     * 反馈分类
     */
    @ApiModelProperty(value = "反馈内容分类",required = true)
    private Byte classify;

    /**
     * 反馈内容
     */
    @ApiModelProperty(value = "反馈内容",required = true)
    private String content;

    /**
     * 用户id
     */
    @ApiModelProperty(hidden = true)
    @Sign
    private Long userId;

    /**
     * 设备品牌
     */
    @ApiModelProperty(hidden = true)
    @Sign
    private String deviceBrand;

    /**
     * 设备型号
     */
    @ApiModelProperty(hidden = true)
    @Sign
    private String deviceModel;

    /**
     * 软件版本
     */
    @ApiModelProperty(hidden = true)
    @Sign
    private String version;

    /**
     * 系统版本
     */
    @ApiModelProperty(hidden = true)
    @Sign
    private String systemVersion;

}
