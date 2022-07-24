package com.eghm.service.pay.vo;

import lombok.Data;

/**
 * @author 二哥很猛
 */
@Data
public class ErrorResponse {

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 错误详情
     */
    private ErrorDetail detail;

    @Data
    public static class ErrorDetail {

        /**
         * 错误字段
         */
        private String field;

        /**
         * 错误值
         */
        private String value;

        /**
         * 错误原因
         */
        private String issue;

        /**
         * 错误位置
         */
        private String location;

    }
}


