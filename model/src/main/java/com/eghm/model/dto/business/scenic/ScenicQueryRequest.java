package com.eghm.model.dto.business.scenic;

import com.eghm.model.annotation.Sign;
import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @date 2022/6/14 23:00
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ScenicQueryRequest extends PagingQuery {

    @ApiModelProperty("上架状态 0:待上架 1:已上架")
    private Integer state;

    @ApiModelProperty(hidden = true, value = "景区所属商户id")
    @Sign
    private Long merchantId;
}
