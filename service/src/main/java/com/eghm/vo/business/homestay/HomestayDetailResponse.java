package com.eghm.vo.business.homestay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2024/6/9
 */

@Data
public class HomestayDetailResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "民宿名称")
    private String title;

    @Schema(description = "民宿所属商家")
    private Long merchantId;

    @Schema(description = "星级 5:五星级 4:四星级 3:三星级 0: 其他")
    private Integer level;

    @Schema(description = "省份")
    private Long provinceId;

    @Schema(description = "城市")
    private Long cityId;

    @Schema(description = "县区")
    private Long countyId;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "描述信息")
    private String intro;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "详细介绍")
    private String introduce;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "特色服务")
    private String keyService;

    @Schema(description = "入住须知")
    private String notesIn;

    @Schema(description = "标签")
    private String tag;
}
