package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统缓存
 *
 * @author 二哥很猛
 */
@Data
@TableName("sys_cache")
public class SysCache {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("缓存名称")
    private String title;

    @ApiModelProperty("缓存名称 必须与CacheConstant中保持一致")
    private String cacheName;

    @ApiModelProperty("缓存更新状态 0:未更新 1:更新成功 2:更新失败")
    private Integer state;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("备注说明")
    private String remark;

}