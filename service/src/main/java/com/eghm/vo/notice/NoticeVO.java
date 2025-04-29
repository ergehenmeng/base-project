package com.eghm.vo.notice;

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

    @ApiModelProperty("公告类型")
    private String noticeType;

    @ApiModelProperty("封面图片")
    private String coverUrl;

}
