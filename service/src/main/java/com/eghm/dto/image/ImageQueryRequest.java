package com.eghm.dto.image;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2018/11/28 15:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ImageQueryRequest extends PagingQuery {

    @ApiModelProperty("图片分类(数据字典image_type)")
    private Integer imageType;

}
