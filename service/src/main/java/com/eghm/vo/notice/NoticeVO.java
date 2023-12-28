package com.eghm.vo.notice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公告置顶vo
 * @author 二哥很猛
 * @date 2019/11/25 15:30
 */
@Data
public class NoticeVO {

    @ApiModelProperty("公告id")
    private Long id;

    @ApiModelProperty("公告名称")
    private String title;
}
