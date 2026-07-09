package com.eghm.infrastructure.persistence.mybatis.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 后台系统操作日志
 *
 * @author 二哥很猛
 */
@Data
@TableName("manage_log")
public class ManageLogPO {
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
}
