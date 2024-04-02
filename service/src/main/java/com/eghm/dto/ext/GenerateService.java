package com.eghm.dto.ext;

import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/4/2
 */

@Data
public class GenerateService {

    private String implPackage;

    private String fileName;

    private String fileFullName;

    private String requestPackage;

    private String selectRequest;

    private String createRequest;

    private String updateRequest;

    private String response;

}
