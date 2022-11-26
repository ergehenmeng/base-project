package com.eghm.model.dto.business.homestay;

import com.eghm.model.validation.annotation.OptionInt;
import com.eghm.model.validation.annotation.Phone;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author 二哥很猛 2022/6/25 14:31
 */
@Data
public class HomestayEditRequest {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "民宿名称", required = true)
    @Size(min = 2, max = 20,message = "民宿名称长度2~20位")
    private String title;

    @ApiModelProperty(value = "星级 5:五星级 4:四星级 3:三星级 0: 其他", required = true)
    @OptionInt(value = {0, 3, 4, 5}, message = "民宿星级非法")
    private Integer level;

    @ApiModelProperty(value = "省份", required = true)
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "省份不能为空")
    private Long provinceId;

    @ApiModelProperty(value = "城市", required = true)
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "城市不能为空")
    private Long cityId;

    @ApiModelProperty(value = "县区", required = true)
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "县区不能为空")
    private Long countyId;

    @ApiModelProperty(value = "详细地址", required = true)
    @Size(min = 2, max = 20,message = "详细地址长度2~50位")
    private String detailAddress;

    @ApiModelProperty(value = "经度", required = true)
    @NotNull(message = "经度不能为空")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度", required = true)
    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;

    @ApiModelProperty(value = "描述信息", required = true)
    @NotBlank(message = "描述信息不能为空")
    @Size(min = 2, max = 50, message = "描述信息长度2~50位")
    private String depict;

    @ApiModelProperty(value = "封面图片,逗号分隔")
    @NotBlank(message = "封面图片不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "详细介绍")
    @NotBlank(message = "详细介绍不能为空")
    private String introduce;

    @ApiModelProperty(value = "联系电话")
    @Phone(message = "联系电话格式错误")
    private String phone;

    @ApiModelProperty(value = "基础设施,逗号分隔")
    private String infrastructure;

    @ApiModelProperty(value = "卫浴设施,逗号分隔")
    private String bathroom;

    @ApiModelProperty(value = "厨房设施,逗号分隔")
    private String kitchen;

    @ApiModelProperty(value = "特色服务,逗号分隔")
    private String keyService;

    @ApiModelProperty(value = "标签,逗号分隔")
    private String tags;

}
