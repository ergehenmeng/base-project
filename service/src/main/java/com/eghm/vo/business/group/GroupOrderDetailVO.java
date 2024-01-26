package com.eghm.vo.business.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/24
 */

@Data
public class GroupOrderDetailVO {

    @ApiModelProperty("拼团人员列表")
    private List<GroupMemberVO> memberList;

    @ApiModelProperty("成团人数")
    private Integer bookingNum;

    @ApiModelProperty("商品ID")
    private Long itemId;

    @ApiModelProperty("商品名称")
    private String itemName;

    @ApiModelProperty("商品图")
    private String itemCoverUrl;
}
