package com.eghm.vo.version;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回移动端版本的更新信息
 *
 * @author 二哥很猛
 * @since 2019/8/22 16:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppVersionVO {

    @Schema(description = "是否为最新版本 true:是 false:否")
    private Boolean latest;

    @Schema(description = "最新版本号(非最新版本时才有该值)")
    private String version;

    @Schema(description = "是否为强制更新的版本(非最新版本时才有该值)")
    private Boolean forceUpdate;

    @Schema(description = "新版本下载的地址(非最新版本时才有该值)")
    private String url;

    @Schema(description = "版本更新说明(非最新版本时才有该值)")
    private String remark;
}
