package com.eghm.vo.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 二哥很猛
 * @since 2025/7/17
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TotpLoginResponse {

    @Schema(description = "登录成功后的信息")
    private LoginResponse data;

    @Schema(description = "状态码 1: 登录成功 2: 需要输入双因子校验码(验证码+uuid) 3: 用户尚未绑定双因子验证")
    private Integer state;

    @Schema(description = "双因子序列号")
    private String uuid;

    @Schema(description = "二维码(base64,绑定双因子使用)")
    private String qrcode;

    @Schema(description = "双因子key")
    private String secretKey;

    /**
     * 登录成功
     *
     * @param data 登录成功的数据
     * @return TotpLoginResponse
     */
    public static TotpLoginResponse success(LoginResponse data) {
        TotpLoginResponse response = new TotpLoginResponse();
        response.setData(data);
        response.setState(1);
        return response;
    }

    /**
     * 需要输入双因子校验码
     *
     * @param uuid uuid
     * @return TotpLoginResponse
     */
    public static TotpLoginResponse needTotp(String uuid) {
        TotpLoginResponse response = new TotpLoginResponse();
        response.setState(2);
        response.setUuid(uuid);
        return response;
    }

    /**
     * 需要绑定双因子
     *
     * @param uuid uuid
     * @param qrcode 二维码
     * @param secretKey 密钥
     * @return TotpLoginResponse
     */
    public static TotpLoginResponse needBindTotp(String uuid, String qrcode, String secretKey) {
        TotpLoginResponse response = new TotpLoginResponse();
        response.setState(3);
        response.setUuid(uuid);
        response.setQrcode(qrcode);
        response.setSecretKey(secretKey);
        return response;
    }
}
