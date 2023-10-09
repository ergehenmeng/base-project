package com.eghm.dto.business.scenic;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.Phone;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @date 2022/6/14 22:24
 */
@Data
public class ScenicAddRequest {

    @ApiModelProperty(value = "景区名称", required = true)
    @NotBlank(message = "景区名称不能为空")
    @Size(min = 2, max = 20, message = "景区名称长度2~20位")
    private String scenicName;

    @ApiModelProperty(value = "景区等级 5: 5A 4: 4A 3:3A 2:2A 1:A 0:其他", required = true)
    @OptionInt(value = {0, 1, 2, 3, 4, 5}, message = "景区等级格式错误")
    private Integer level;

    @ApiModelProperty(value = "景区营业时间", required = true)
    @NotBlank(message = "营业时间不能为空")
    @Size(max = 100, message = "营业时间长度2~100位")
    private String openTime;

    @ApiModelProperty(value = "景区电话", required = true)
    @Phone(message = "景区电话格式错误")
    private String phone;

    @ApiModelProperty(value = "景区标签")
    private String tag;

    @ApiModelProperty(value = "省份id", required = true)
    @NotNull(message = "请选择省份")
    private Long provinceId;

    @ApiModelProperty(value = "城市id", required = true)
    @NotNull(message = "请选择城市")
    private Long cityId;

    @ApiModelProperty(value = "县区id", required = true)
    @NotNull(message = "请选择县区")
    private Long countyId;

    @ApiModelProperty(value = "详细地址", required = true)
    @NotBlank(message = "详细地址不能为空")
    @Size(max = 100, message = "详细地址长度1~100位")
    private String detailAddress;

    @ApiModelProperty(value = "经度", required = true)
    @NotNull(message = "经度不能能为空")
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度", required = true)
    @NotNull(message = "维度不能为空")
    @DecimalMin(value = "-90", message = "纬度应[-90, 90]范围内")
    @DecimalMax(value = "90", message = "纬度应[-90, 90]范围内")
    private BigDecimal latitude;

    @ApiModelProperty(value = "景区描述信息", required = true)
    @NotBlank(message = "景区描述信息不能为空")
    @Size(max = 50, message = "景区描述信息最大50位")
    private String depict;

    @ApiModelProperty(value = "景区图片", required = true)
    @NotBlank(message = "景区图片不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "景区详细介绍信息", required = true)
    @NotBlank(message = "景区详细介绍不能为空")
    private String introduce;

    /**
     * 编辑时该字段不更新该字段
     */
    @ApiModelProperty(value = "景区所属商户id", hidden = true)
    @Assign
    private Long merchantId;
}
