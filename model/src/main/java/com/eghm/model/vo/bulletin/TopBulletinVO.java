package com.eghm.model.vo.bulletin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 公告置顶vo
 * @author 二哥很猛
 * @date 2019/11/25 15:30
 */
@Data
@ApiModel
public class TopBulletinVO implements Serializable {

    /**
     * 公告id
     */
    @ApiModelProperty("公告id")
    private Integer id;

    /**
     * 公告标题
     */
    @ApiModelProperty("公告名称")
    private String title;
}
