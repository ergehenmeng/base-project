package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.State;
import com.eghm.validation.annotation.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>
 * 店铺信息表
 * </p>
 * 校验不能删除,上架会使用
 * @author 二哥很猛
 * @since 2022-07-01
 */
@Data
@TableName("item_store")
@EqualsAndHashCode(callSuper = true)
public class ItemStore extends BaseEntity {

    @Schema(description = "店铺名称")
    @NotBlank(message = "店铺名称不能为空")
    private String title;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @Schema(description = "所属商户id")
    private Long merchantId;

    @Schema(description = "店铺logo")
    @NotBlank(message = "店铺logo不能为空")
    private String logoUrl;

    @Schema(description = "封面图")
    @NotBlank(message = "店铺封面图不能为空")
    private String coverUrl;

    @Schema(description = "营业时间")
    @NotBlank(message = "营业时间不能为空")
    private String openTime;

    @Schema(description = "省id")
    @NotNull(message = "省份不能为空")
    private Long provinceId;

    @Schema(description = "城市id")
    @NotNull(message = "城市不能为空")
    private Long cityId;

    @Schema(description = "县区id")
    @NotNull(message = "县区不能为空")
    private Long countyId;

    @Schema(description = "详细地址")
    @NotBlank(message = "详细地址不能为空")
    private String detailAddress;

    @Schema(description = "经度")
    @NotNull(message = "经度不能为空")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;

    @Schema(description = "商家电话")
    @Phone(message = "商家电话格式错误")
    private String telephone;

    @Schema(description = "商家介绍")
    @NotBlank(message = "商家介绍不能为空")
    private String introduce;

    @Schema(description = "是否为推荐店铺 true:是 false:不是")
    private Boolean recommend;

    @Schema(description = "评分")
    private BigDecimal score;

    @Schema(description = "仓库地址id")
    @NotNull(message = "仓库地址不能为空")
    private Long depotAddressId;
}
