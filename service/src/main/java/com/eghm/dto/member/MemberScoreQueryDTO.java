package com.eghm.dto.member;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 殿小二
 * @since 2020/9/7
 */
@Getter
@Setter
@ToString(callSuper = true)
public class MemberScoreQueryDTO extends PagingQuery {

    @Schema(description = "积分类型")
    private Integer type;

    @Assign
    @Schema(description = "登录人id", hidden = true)
    private Long memberId;
}
