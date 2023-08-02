package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.LongToIpEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
@TableName("black_roster")
public class BlackRoster extends BaseEntity {

    @ApiModelProperty("访问ip")
    @JsonSerialize(using = LongToIpEncoder.class)
    private Long startIp;

    @ApiModelProperty("数字ip")
    @JsonSerialize(using = LongToIpEncoder.class)
    private Long endIp;
}