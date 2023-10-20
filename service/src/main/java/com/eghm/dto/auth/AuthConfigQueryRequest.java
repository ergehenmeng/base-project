package com.eghm.dto.auth;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.SignType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthConfigQueryRequest extends PagingQuery {

    @ApiModelProperty("签名方式")
    private SignType signType;

}
