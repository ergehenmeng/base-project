package com.eghm.dto.business.line;

import com.eghm.enums.ref.RefundType;
import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.RangeInt;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/26
 */
@Data
public class LineAddRequest {

    @ApiModelProperty(value = "所属旅行社id", required = true)
    @NotNull(message = "所属旅行社不能为空")
    private Long travelAgencyId;

    @ApiModelProperty(value = "线路名称", required = true)
    @Size(min = 2, max = 20, message = "线路名称长度2~20字符")
    @NotBlank(message = "线路名称不能为空")
    @WordChecker
    private String title;

    @ApiModelProperty(value = "出发省份id", required = true)
    @NotNull(message = "出发省份不能为空")
    private Long startProvinceId;

    @ApiModelProperty(value = "出发城市id", required = true)
    @NotNull(message = "出发城市不能为空")
    private Long startCityId;

    @ApiModelProperty(value = "封面图片", required = true)
    @NotBlank(message = "封面图片不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "虚拟销售量", required = true)
    @Min(value = 0, message = "虚拟销量不能小于0")
    private Integer virtualNum;

    @ApiModelProperty(value = "几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10: 十日游 11:十一日游 12:十二日游 13:十三日游 14:十四日游 15:十五日游", required = true)
    @OptionInt(value = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, message = "旅游天数1~15天")
    private Integer duration;

    @ApiModelProperty(value = "提前天数", required = true)
    @RangeInt(max = 30, message = "提前天数应为0~30天")
    private Integer advanceDay;

    @ApiModelProperty(value = "是否支持退款 0:不支持 1:直接退款 2:审核后退款", required = true)
    @NotNull(message = "退款方式不能为空")
    private RefundType refundType;

    @ApiModelProperty(value = "退款描述", required = true)
    @Size(max = 100, message = "退款描述长度最大100字符")
    @WordChecker
    private String refundDescribe;

    @ApiModelProperty(value = "商品介绍", required = true)
    @NotBlank(message = "商品介绍不能为空")
    @WordChecker
    private String introduce;

    @ApiModelProperty(value = "每日配置信息", required = true)
    @NotEmpty(message = "每日线路配置不能为空")
    private List<LineDayConfigRequest> configList;
}
