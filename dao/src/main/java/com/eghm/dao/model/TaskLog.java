package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("task_log")
public class TaskLog extends BaseEntity {

    @ApiModelProperty("任务nid")
    private String nid;

    @ApiModelProperty("定时任务bean名称")
    private String beanName;

    @ApiModelProperty("执行结果 0:失败 1:成功")
    private Boolean state;

    @ApiModelProperty("执行结束时间")
    private Long elapsedTime;

    @ApiModelProperty("执行任务的机器ip")
    private String ip;

    @ApiModelProperty("执行错误时的信息")
    private String errorMsg;

}