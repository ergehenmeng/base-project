package com.eghm.dto.member;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

/**
 * @author 殿小二
 * @since 2020/9/7
 */
@Getter
@Setter
@ToString(callSuper = true)
public class MemberScoreQueryRequest extends PagingQuery {

    @Schema(description = "积分类型")
    private Integer type;

    @Schema(description = "登录人id")
    @NotNull(message = "登录人id不能为空")
    private Long memberId;

}
