package com.eghm.vo.business.homestay;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/1/9
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomestayVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "民宿名称")
    private String title;

    @Schema(description = "星级 5:五星级 4:四星级 3:三星级 0:其他")
    private Integer level;

    @Schema(description = "城市")
    @JsonIgnore
    private Long cityId;

    @Schema(description = "县区")
    @JsonIgnore
    private Long countyId;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "距离 单位:m")
    private Integer distance;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "最低价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer minPrice;

    @Schema(description = "标签")
    @JsonIgnore
    private String tagIds;

    @Schema(description = "标签列表")
    private List<String> tagList;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private Integer state;
}
