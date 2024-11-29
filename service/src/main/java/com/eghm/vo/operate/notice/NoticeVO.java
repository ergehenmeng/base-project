package com.eghm.vo.operate.notice;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公告置顶vo
 *
 * @author 二哥很猛
 * @since 2019/11/25 15:30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoticeVO {

    @ApiModelProperty("公告id")
    private Long id;

    @ApiModelProperty("公告名称")
    private String title;

    @ApiModelProperty("公告类型名称")
    private String noticeType;

    @ApiModelProperty("封面图片")
    private String coverUrl;

    @ApiModelProperty("状态 false:未上架 true:已上架")
    private Boolean state;

    @ApiModelProperty("是否已删除 false:未删除 true:已删除")
    private Boolean deleted;

}
