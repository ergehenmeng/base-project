package com.eghm.vo.template;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
public class PushTemplateResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("消息名称")
    private String title;

    @ApiModelProperty("消息nid")
    private String nid;

    @ApiModelProperty("状态 0:关闭 1:开启")
    private Boolean state;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("后置处理标示符(消息推送跳转页面)")
    private String tag;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}