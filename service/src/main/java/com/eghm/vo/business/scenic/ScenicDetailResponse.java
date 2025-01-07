package com.eghm.vo.business.scenic;

import com.eghm.enums.ref.State;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2024/6/3
 */

@Data
public class ScenicDetailResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "景区名称")
    private String scenicName;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @Schema(description = "景区等级 5: 5A 4: 4A 3: 3A 0:其他")
    private Integer level;

    @Schema(description = "景区标签")
    private String tag;

    @Schema(description = "景区营业时间")
    private String openTime;

    @Schema(description = "景区电话")
    private String phone;

    @Schema(description = "景区图片")
    private String coverUrl;

    @Schema(description = "省份id")
    private Long provinceId;

    @Schema(description = "城市id")
    private Long cityId;

    @Schema(description = "县区id")
    private Long countyId;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "景区描述信息")
    private String depict;

    @Schema(description = "景区详细介绍信息")
    private String introduce;

}
