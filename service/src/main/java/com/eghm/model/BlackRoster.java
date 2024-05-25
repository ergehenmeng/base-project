package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.LongToIpEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("black_roster")
@EqualsAndHashCode(callSuper = true)
public class BlackRoster extends BaseEntity {

    @ApiModelProperty("访问ip")
    @JsonSerialize(using = LongToIpEncoder.class)
    private Long startIp;

    @ApiModelProperty("数字ip")
    @JsonSerialize(using = LongToIpEncoder.class)
    private Long endIp;

    @ApiModelProperty("备注信息")
    private String remark;
}