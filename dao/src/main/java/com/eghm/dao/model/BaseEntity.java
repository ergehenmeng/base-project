package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author wyb
 * @date 2021/12/18 17:12
 */
@Getter
@Setter
public abstract class BaseEntity {

    /**
     * <br>
     *     主键
     * 表 : banner<br>
     * 对应字段 : id<br>
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    private Long id;

    /**
     * 添加时间<br>
     * 表 : banner<br>
     * 对应字段 : add_time<br>
     */
    @ApiModelProperty("添加日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : banner<br>
     * 对应字段 : update_time<br>
     */
    @ApiModelProperty("更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
