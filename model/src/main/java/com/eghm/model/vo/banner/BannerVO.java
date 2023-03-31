package com.eghm.model.vo.banner;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 殿小二
 * @date 2020/9/4
 */
@Data
@ApiModel
public class BannerVO {

    /**
     * 图片标题
     */
    @ApiModelProperty("图片标题")
    private String title;

    /**
     * 图片地址
     */
    @ApiModelProperty("图片地址")
    private String imgUrl;

    /**
     * 是否可以点击
     */
    @ApiModelProperty("是否可点击 true:可以 false:不可以")
    private Boolean click;

    /**
     * 点击后跳转的url
     */
    @ApiModelProperty("点击跳转的url")
    private String turnUrl;

    /**
     * 排序
     */
    @ApiModelProperty("多图时排序规则大<->小")
    private Integer sort;

}
