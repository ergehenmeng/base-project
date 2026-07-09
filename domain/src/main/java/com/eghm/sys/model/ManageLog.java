package com.eghm.sys.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 后台系统操作日志
 *
 * @author 二哥很猛
 */
@Data
public class ManageLog {
    /** id主键 */
    private Long id;

    /** 请求地址 */
    private String url;

    /** 操作人 */
    private Long userId;

    /** 请求参数 */
    private String request;

    /** 添加时间 */
    private LocalDateTime createTime;

    /** 访问ip */
    private String ip;

    /** 业务耗时 */
    private Long businessTime;

    /** 响应参数 */
    private String response;

    public void initialize(String url, Long userId, String request, String ip, Long businessTime, String response) {
        this.url = url;
        this.userId = userId;
        this.request = request;
        this.ip = ip;
        this.businessTime = businessTime;
        this.response = response;
        this.createTime = LocalDateTime.now();
    }
}
