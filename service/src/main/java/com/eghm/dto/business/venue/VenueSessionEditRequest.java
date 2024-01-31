package com.eghm.dto.business.venue;

import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
public class VenueSessionEditRequest {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "所属场馆id")
    @NotNull(message = "请选择所属场馆")
    private Long venueId;

    @ApiModelProperty(value = "场次名称")
    @NotBlank(message = "请填写场次名称")
    @WordChecker(message = "场次名称存在敏感词")
    private String title;

    @ApiModelProperty("场次价格")
    @NotEmpty(message = "请设置场次价格")
    private List<SessionPriceRequest> priceList;
}
