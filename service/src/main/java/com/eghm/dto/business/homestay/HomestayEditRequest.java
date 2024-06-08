package com.eghm.dto.business.homestay;

import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.Phone;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛 2022/6/25 14:31
 */
@Data
public class HomestayEditRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "民宿名称", required = true)
    @Size(min = 2, max = 20, message = "民宿名称长度2~20位")
    @NotBlank(message = "民宿名称不能为空")
    @WordChecker(message = "民宿名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "星级 5:五星级 4:四星级 3:三星级 0:其他", required = true)
    @OptionInt(value = {0, 3, 4, 5}, message = "民宿星级非法")
    private Integer level;

    @ApiModelProperty(value = "省份", required = true)
    @NotNull(message = "省份不能为空")
    private Long provinceId;

    @ApiModelProperty(value = "城市", required = true)
    @NotNull(message = "城市不能为空")
    private Long cityId;

    @ApiModelProperty(value = "县区", required = true)
    @NotNull(message = "县区不能为空")
    private Long countyId;

    @ApiModelProperty(value = "详细地址", required = true)
    @Size(min = 2, max = 20, message = "详细地址长度2~50位")
    @NotBlank(message = "详细地址不能为空")
    @WordChecker(message = "详细地址存在敏感词")
    private String detailAddress;

    @ApiModelProperty(value = "经度", required = true)
    @NotNull(message = "经度不能为空")
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度", required = true)
    @NotNull(message = "纬度不能为空")
    @DecimalMin(value = "-90", message = "纬度应[-90, 90]范围内")
    @DecimalMax(value = "90", message = "纬度应[-90, 90]范围内")
    private BigDecimal latitude;

    @ApiModelProperty(value = "描述信息", required = true)
    @Size(min = 2, max = 50, message = "描述信息长度2~50位")
    @WordChecker(message = "描述信息存在敏感词")
    private String intro;

    @ApiModelProperty(value = "封面图片,逗号分隔", required = true)
    @NotBlank(message = "封面图片不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "详细介绍", required = true)
    @NotBlank(message = "详细介绍不能为空")
    @WordChecker(message = "详细介绍存在敏感词")
    private String introduce;

    @ApiModelProperty(value = "联系电话", required = true)
    @Phone(message = "联系电话格式错误")
    private String phone;

    @ApiModelProperty(value = "入住须知")
    @NotBlank(message = "入住须知不能为空")
    @WordChecker
    private String notesIn;
    
    @ApiModelProperty(value = "特色服务")
    private List<Integer> serviceList;

    @ApiModelProperty(value = "标签,逗号分隔")
    @WordChecker
    private String tag;

}
