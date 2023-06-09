package com.eghm.dto.member;

import com.eghm.annotation.Padding;
import com.eghm.dto.ext.PagingQuery;
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
public class MemberScoreQueryDTO extends PagingQuery {

    @ApiModelProperty("积分类型")
    private Integer type;

    @Padding
    @ApiModelProperty(hidden = true)
    private Long memberId;
}
