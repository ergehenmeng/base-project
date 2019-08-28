package com.fanyin.dao.model.business;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 */
@Data
public class MessageTemplate implements Serializable {

    /**
     * 主键<br>
     * 表 : message_template<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 消息名称<br>
     * 表 : message_template<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 消息nid<br>
     * 表 : message_template<br>
     * 对应字段 : nid<br>
     */
    private String nid;

    /**
     * 状态 0:关闭 1:开启<br>
     * 表 : message_template<br>
     * 对应字段 : state<br>
     */
    private Boolean state;

    /**
     * 消息类型<br>
     * 表 : message_template<br>
     * 对应字段 : classify<br>
     */
    private Byte classify;

    /**
     * 消息内容<br>
     * 表 : message_template<br>
     * 对应字段 : content<br>
     */
    private String content;

    /**
     * 更新时间<br>
     * 表 : message_template<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 后置处理标示符(消息推送跳转页面)<br>
     * 表 : message_template<br>
     * 对应字段 : tag<br>
     */
    private String tag;

    /**
     * 备注信息<br>
     * 表 : message_template<br>
     * 对应字段 : remark<br>
     */
    private String remark;

    private static final long serialVersionUID = 1L;

}