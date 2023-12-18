package com.eghm.vo.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/12/18
 */

@Data
public class DictResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("字典中文名称")
    private String title;

    @ApiModelProperty("字典编码")
    private String nid;

    @ApiModelProperty("锁定状态(禁止删除):0:未锁定 1:锁定")
    private Boolean locked;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("数据项列表")
    private List<DictItemResponse> itemList;
}
