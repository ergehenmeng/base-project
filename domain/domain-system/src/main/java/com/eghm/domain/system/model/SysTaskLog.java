package com.eghm.domain.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysTaskLog {
    /** id主键 */
    private Long id;

    /** 定时任务bean名称 */
    private String beanName;

    /** 方法名称 */
    private String methodName;

    /** 方法入参 */
    private String args;

    /** 执行结果 0:失败 1:成功 */
    private Boolean state;

    /** 执行时间 */
    private LocalDateTime startTime;

    /** 任务耗时,单位:ms */
    private Long elapsedTime;

    /** 执行任务的机器ip */
    private String ip;

    /** 执行错误时的信息 */
    private String errorMsg;

    public void initialize(String beanName, String methodName, String args, Long startTime, Long elapsedTime,
                           String ip, String errorMsg) {
        this.beanName = beanName;
        this.methodName = methodName;
        this.args = args;
        this.startTime = LocalDateTime.ofEpochSecond(startTime / 1000, 0, java.time.ZoneOffset.of("+8"));
        this.elapsedTime = elapsedTime;
        this.ip = ip;
        this.errorMsg = errorMsg;
        this.state = errorMsg == null || errorMsg.isEmpty();
    }

    public boolean isSuccess() {
        return Boolean.TRUE.equals(this.state);
    }
}
