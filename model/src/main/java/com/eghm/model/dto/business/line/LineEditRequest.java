package com.eghm.model.dto.business.line;

import com.eghm.common.enums.ref.RefundType;
import com.eghm.model.validation.annotation.OptionInt;
import com.eghm.model.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/26
 */
@Data
public class LineEditRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "所属旅行社id", required = true)
    @NotNull(message = "所属旅行社不能为空")
    private Long travelAgencyId;

    @ApiModelProperty(value = "线路名称", required = true)
    @Size(min = 2, max = 20, message = "线路名称长度2~20字符")
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

    @ApiModelProperty(value = "虚拟销售量")
    @Size(max = 9999, message = "虚拟销量应为0~9999")
    private Integer virtualNum;

    @ApiModelProperty(value = "几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10: 10日游 11:11日游 12:十二日游")
    @OptionInt(value = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, message = "旅游天数1~12天")
    private Integer duration;

    @ApiModelProperty(value = "提前天数")
    @RangeInt(max = 30, message = "提前天数应为0~30天")
    private Integer advanceDay;

    @ApiModelProperty(value = "是否支持退款 0:不支持 1:直接退款 2:审核后退款")
    @NotNull(message = "退款方式不能为空")
    private RefundType refundType;

    @ApiModelProperty(value = "退款描述")
    @Size(max = 100, message = "退款描述长度最大100字符")
    private String refundDescribe;

    @ApiModelProperty(value = "商品介绍")
    @NotBlank(message = "商品介绍不能为空")
    private String introduce;

    @NotEmpty(message = "每日线路配置不能为空")
    @ApiModelProperty("每日配置信息")
    private List<LineDayConfigRequest> configList;
}
