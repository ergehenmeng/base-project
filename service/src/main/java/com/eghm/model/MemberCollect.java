package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.CollectType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 会员收藏记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("member_collect")
public class MemberCollect extends BaseEntity {

    @ApiModelProperty(value = "会员id")
    private Long memberId;

    @ApiModelProperty(value = "收藏id")
    private Long collectId;

    @ApiModelProperty(value = "收藏对象类型")
    private CollectType collectType;

    @ApiModelProperty(value = "0:取消收藏, 1:加入收藏")
    private Integer state;

}
