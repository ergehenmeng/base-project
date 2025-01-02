package com.eghm.dto.ext;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 二哥很猛
 * @since 2019/8/28 17:59
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilePath {

    @Schema(description = "文件相对路径")
    private String path;

    @Schema(description = "ip+端口号")
    private String address;

    @Schema(description = "大小")
    private Long size;
}
