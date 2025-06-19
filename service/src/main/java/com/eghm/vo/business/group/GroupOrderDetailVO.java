package com.eghm.vo.business.group;

import com.eghm.convertor.SplitterIndexSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/24
 */

@Data
public class GroupOrderDetailVO {

    @Schema(description = "拼团人员列表")
    private List<GroupMemberVO> memberList;

    @Schema(description = "成团人数")
    private Integer bookingNum;

    @Schema(description = "当前用户是否已在团中")
    private Boolean inGroup;

    @Schema(description = "商品ID")
    private Long itemId;

    @Schema(description = "商品名称")
    private String itemName;

    @Schema(description = "商品图")
    @JsonSerialize(using = SplitterIndexSerializer.class)
    private String itemCoverUrl;
}
