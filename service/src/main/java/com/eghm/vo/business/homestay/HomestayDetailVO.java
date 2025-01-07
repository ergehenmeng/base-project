package com.eghm.vo.business.homestay;

import com.eghm.vo.business.homestay.room.HomestayRoomListVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/1/12
 */

@Data
public class HomestayDetailVO {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "民宿名称")
    private String title;

    @Schema(description = "星级 5:五星级 4:四星级 3:三星级 0:其他")
    private Integer level;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "描述信息")
    private String intro;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "详细介绍")
    private String introduce;

    @Schema(description = "是否收藏")
    private Boolean collect;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "入住须知")
    private String notesIn;

    @Schema(description = "特色服务")
    private String keyService;

    @Schema(description = "分数")
    private BigDecimal score;

    @Schema(description = "标签")
    private List<String> tagList;

    @Schema(description = "推荐房型列表")
    private List<HomestayRoomListVO> recommendRoomList;
}
