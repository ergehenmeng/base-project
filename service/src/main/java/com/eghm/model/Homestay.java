package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.State;
import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>
 * 民宿信息表
 * </p>
 * 校验不能删除,上架会使用
 * @author 二哥很猛
 * @since 2022-06-25
 */
@Data
@TableName("homestay")
@EqualsAndHashCode(callSuper = true)
public class Homestay extends BaseEntity {

    @Schema(description = "民宿名称")
    @NotBlank(message = "民宿名称不能为空")
    private String title;

    @Schema(description = "民宿所属商家")
    private Long merchantId;

    @Schema(description = "星级 5:五星级 4:四星级 3:三星级 0: 其他")
    @OptionInt(value = {0, 3, 4, 5}, message = "民宿星级非法")
    private Integer level;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @Schema(description = "省份")
    @NotNull(message = "省份不能为空")
    private Long provinceId;

    @Schema(description = "城市")
    @NotNull(message = "城市不能为空")
    private Long cityId;

    @Schema(description = "县区")
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

    @Schema(description = "描述信息")
    @NotBlank(message = "描述信息不能为空")
    private String intro;

    @Schema(description = "封面图片")
    @NotBlank(message = "封面图片不能为空")
    private String coverUrl;

    @Schema(description = "详细介绍")
    @NotBlank(message = "详细介绍不能为空")
    private String introduce;

    @Schema(description = "联系电话")
    @Phone(message = "联系电话格式错误")
    private String phone;

    @Schema(description = "特色服务")
    private String keyService;

    @Schema(description = "入住须知")
    private String notesIn;

    @Schema(description = "标签")
    private String tag;

    @Schema(description = "分数")
    private BigDecimal score;
}
