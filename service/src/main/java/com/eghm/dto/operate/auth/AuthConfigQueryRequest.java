package com.eghm.dto.operate.auth;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.SignType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthConfigQueryRequest extends PagingQuery {

    @Schema(description = "签名方式")
    private SignType signType;

}
