package com.eghm.model.dto.homestay.room;

import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @date 2022/6/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HomestayRoomQueryRequest extends PagingQuery {

    @ApiModelProperty("上架状态 0:待上架 1:已上架")
    private Integer state;

}
