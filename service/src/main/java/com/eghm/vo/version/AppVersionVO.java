package com.eghm.vo.version;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回移动端版本的更新信息
 *
 * @author 二哥很猛
 * @date 2019/8/22 16:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppVersionVO {

    @ApiModelProperty(value = "是否为最新版本 true:是 false:否")
    private Boolean latest;

    @ApiModelProperty(value = "最新版本号")
    private String version;

    @ApiModelProperty(value = "是否为强制更新的版本")
    private Boolean forceUpdate;

    @ApiModelProperty(value = "新版本下载的地址")
    private String url;

    @ApiModelProperty(value = "版本更新说明")
    private String remark;
}
