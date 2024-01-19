package com.eghm.vo.banner;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 殿小二
 * @since 2020/9/4
 */
@Data
public class BannerVO {

    @ApiModelProperty("图片标题")
    private String title;

    @ApiModelProperty("图片地址")
    private String imgUrl;

    @ApiModelProperty("是否可点击 true:可以 false:不可以")
    private Boolean click;

    @ApiModelProperty("点击跳转的url")
    private String turnUrl;

    @ApiModelProperty("多图时排序规则大<->小")
    private Integer sort;

}
