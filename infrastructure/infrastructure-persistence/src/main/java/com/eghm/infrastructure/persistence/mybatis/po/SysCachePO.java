package com.eghm.infrastructure.persistence.mybatis.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统缓存
 *
 * @author 二哥很猛
 */
@Data
@TableName("sys_cache")
public class SysCachePO {

    /** 主键 */
    private Long id;

    /** 缓存名称 */
    private String title;

    /** 缓存名称 必须与CacheConstant中保持一致 */
    private String cacheName;

    /** 缓存更新状态 0:未更新 1:更新成功 2:更新失败 */
    private Integer state;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 备注说明 */
    private String remark;

}
