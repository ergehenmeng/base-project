package com.eghm.dto.member;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author 殿小二
 * @since 2020/9/7
 */
@Getter
@Setter
@ToString(callSuper = true)
public class MemberScoreQueryRequest extends PagingQuery {

    @ApiModelProperty("积分类型")
    private Integer type;

    @ApiModelProperty(value = "登录人id")
    @NotNull(message = "登录人id不能为空")
    private Long memberId;

}
