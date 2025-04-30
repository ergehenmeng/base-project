package com.eghm.vo.business.homestay;

import com.eghm.convertor.SplitterArrayIntSerializer;
import com.eghm.convertor.SplitterArraySerializer;
import com.eghm.vo.business.homestay.room.HomestayRoomListVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/1/12
 */

@Data
public class HomestayDetailVO {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "民宿名称")
    private String title;

    @ApiModelProperty(value = "星级 5:五星级 4:四星级 3:三星级 0:其他")
    private Integer level;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "描述信息")
    private String intro;

    @ApiModelProperty(value = "封面图片")
    @JsonSerialize(using = SplitterArraySerializer.class)
    private String coverUrl;

    @ApiModelProperty(value = "详细介绍")
    private String introduce;

    @ApiModelProperty("是否收藏")
    private Boolean collect;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "入住须知")
    private String notesIn;

    @ApiModelProperty(value = "特色服务")
    @JsonSerialize(using = SplitterArrayIntSerializer.class)
    private String keyService;

    @ApiModelProperty("分数")
    private BigDecimal score;

    @ApiModelProperty(value = "距离 单位:m")
    private Integer distance;

    @ApiModelProperty(value = "标签")
    @JsonSerialize(using = SplitterArraySerializer.class)
    private List<String> tag;

    @ApiModelProperty("推荐房型列表")
    private List<HomestayRoomListVO> recommendRoomList;
}
