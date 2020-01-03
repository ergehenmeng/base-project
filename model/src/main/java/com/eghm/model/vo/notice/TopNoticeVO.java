package com.eghm.model.vo.notice;

import lombok.Data;

import java.io.Serializable;

/**
 * 公告置顶vo
 * @author 二哥很猛
 * @date 2019/11/25 15:30
 */
@Data
public class TopNoticeVO implements Serializable {

    /**
     * 公告id
     */
    private Integer id;

    /**
     * 公告标题
     */
    private String title;
}
