package com.eghm.vo.sys.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/5/24
 */
@Data
public class BaseDictResponse {

    @Schema(description = "数据字典nid")
    private String nid;

    @Schema(description = "对应字典列表")
    private List<BaseItemVO> itemList;
}
