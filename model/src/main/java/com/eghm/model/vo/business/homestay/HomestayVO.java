package com.eghm.model.vo.business.homestay;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.eghm.model.vo.business.homestay.room.HomestayRoomListVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/1/12
 */

@Data
public class HomestayVO {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "民宿名称")
    private String title;

    @ApiModelProperty(value = "星级 5:五星级 4:四星级 3:三星级 0: 其他")
    private Integer level;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal latitude;

    @ApiModelProperty(value = "描述信息")
    private String intro;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "详细介绍")
    private String introduce;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "基础设施")
    private String infrastructure;

    @ApiModelProperty(value = "卫浴设施")
    private String bathroom;

    @ApiModelProperty(value = "厨房设施")
    private String kitchen;

    @ApiModelProperty(value = "特色服务")
    private String keyService;

    @ApiModelProperty(value = "标签")
    private List<String> tagList;

    @ApiModelProperty("推荐房型列表")
    private List<HomestayRoomListVO> recommendRoomList;
}
