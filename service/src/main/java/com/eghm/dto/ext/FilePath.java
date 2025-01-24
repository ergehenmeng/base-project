package com.eghm.dto.ext;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author 二哥很猛
 * @since 2019/8/28 17:59
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FilePath(@Schema(description = "文件相对路径") String path, @Schema(description = "文件地址") String address, @Schema(description = "文件大小") Long size) {
}
