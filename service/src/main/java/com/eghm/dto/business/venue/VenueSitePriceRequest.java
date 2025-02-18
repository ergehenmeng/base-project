package com.eghm.dto.business.venue;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.AbstractDateComparator;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class VenueSitePriceRequest extends AbstractDateComparator {

    @Assign
    @ApiModelProperty(value = "所属场馆", hidden = true)
    private Long venueId;

    @ApiModelProperty(value = "场地id", required = true)
    @NotNull(message = "请选择场地")
    private Long venueSiteId;

    @ApiModelProperty(value = "周期 1:星期一 2:星期二 ... 7:星期日", required = true)
    @NotEmpty(message = "请选择周期")
    public transient List<Integer> week;

    @ApiModelProperty(value = "开始日期yyyy-MM-dd", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    @ApiModelProperty(value = "截止日期yyyy-MM-dd", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "截止日期不能为空")
    private LocalDate endDate;

    @ApiModelProperty(value = "场次价格",required = true)
    @NotEmpty(message = "请设置场次价格")
    private transient List<PriceRequest> priceList;
}
