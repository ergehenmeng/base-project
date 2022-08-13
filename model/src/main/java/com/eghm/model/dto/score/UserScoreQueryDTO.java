package com.eghm.model.dto.score;

import com.eghm.model.annotation.Sign;
import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 殿小二
 * @date 2020/9/7
 */
@Getter
@Setter
@ToString(callSuper = true)
public class UserScoreQueryDTO extends PagingQuery {

    @ApiModelProperty("积分类型")
    private Byte type;

    @Sign
    @ApiModelProperty(hidden = true)
    private Long userId;
}
