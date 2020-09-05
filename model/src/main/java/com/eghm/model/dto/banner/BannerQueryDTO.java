package com.eghm.model.dto.banner;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 殿小二
 * @date 2020/9/4
 */
@Data
public class BannerQueryDTO {

    /**
     * 轮播图分类类型 sys_dict表中 banner_classify指定
     */
    @ApiModelProperty(value = "轮播图类型", required = true)
    private Byte classify;
}
