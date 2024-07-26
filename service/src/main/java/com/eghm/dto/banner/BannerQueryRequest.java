package com.eghm.dto.banner;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2019/8/22 11:25
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class BannerQueryRequest extends PagingQuery {

    @ApiModelProperty("轮播图类型")
    private Integer bannerType;

    @ApiModelProperty("客户端类型 PC, ANDROID, IOS, H5, WECHAT ")
    private String clientType;

    @ApiModelProperty("播放时间(在该时间段播放) yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime middleTime;

    @ApiModelProperty("状态 true:正常 false:禁用")
    private Boolean state;
}
