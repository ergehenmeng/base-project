package com.eghm.vo.banner;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 殿小二
 * @since 2020/9/4
 */
@Data
public class BannerVO {

    @Schema(description = "图片标题")
    private String title;

    @Schema(description = "图片地址")
    private String imgUrl;

    @Schema(description = "是否可点击 true:可以 false:不可以")
    private Boolean click;

    @Schema(description = "点击跳转的url")
    private String jumpUrl;

}
