package com.eghm.vo.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/5/24
 */
@Data
public class LoadDictResponse {

    @ApiModelProperty("数据字典nid")
    private String nid;

    @ApiModelProperty("对应字典列表")
    private List<DictVO> itemList;
}
