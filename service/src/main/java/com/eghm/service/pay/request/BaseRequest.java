package com.eghm.service.pay.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseRequest {

    @JsonProperty("mchid")
    private String merchantId;
}
