package com.eghm.dao.model;

import lombok.*;

import java.util.Date;

/**
 * 系统异常记录
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionLog extends BaseEntity {

    /**
     * 访问链接<br>
     * 表 : exception_log<br>
     * 对应字段 : url<br>
     */
    private String url;

    /**
     * 请求参数(json)<br>
     * 表 : exception_log<br>
     * 对应字段 : request_param<br>
     */
    private String requestParam;

    /**
     * 添加时间<br>
     * 表 : exception_log<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 错误日志<br>
     * 表 : exception_log<br>
     * 对应字段 : error_msg<br>
     */
    private String errorMsg;

    private static final long serialVersionUID = 1L;
}