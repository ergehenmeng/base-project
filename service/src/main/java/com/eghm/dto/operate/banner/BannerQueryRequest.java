package com.eghm.dto.operate.banner;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "轮播图类型")
    private Integer bannerType;

    @Schema(description = "客户端类型 PC, ANDROID, IOS, H5, WECHAT ")
    private String clientType;

    @Schema(description = "播放时间(在该时间段播放) yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime middleTime;

    @Schema(description = "状态 true:正常 false:禁用")
    private Boolean state;
}
