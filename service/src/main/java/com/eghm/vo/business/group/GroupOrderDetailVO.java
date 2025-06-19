package com.eghm.vo.business.group;

import com.eghm.convertor.SplitterIndexSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    @JsonSerialize(using = SplitterIndexSerializer.class)
    private String itemCoverUrl;

    @ApiModelProperty("当前用户是否已在团中")
    private Boolean inGroup;
}
