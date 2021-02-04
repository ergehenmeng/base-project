package com.eghm.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统异常记录
 * @author 二哥很猛
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionLog implements Serializable {
    /**
     * 主键<br>
     * 表 : exception_log<br>
     * 对应字段 : id<br>
     */
    private Long id;

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