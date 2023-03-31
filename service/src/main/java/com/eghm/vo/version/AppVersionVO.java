package com.eghm.vo.version;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 返回移动端版本的更新信息
 * @author 二哥很猛
 * @date 2019/8/22 16:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class AppVersionVO implements Serializable {

    private static final long serialVersionUID = -1238037314236966606L;

    /**
     * 是否为最新版本
     */
    @ApiModelProperty(value = "是否为最新版本 true:是 false:否")
    private Boolean latest;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "最新版本号")
    private String version;

    /**
     * 是否为强制更新版本
     */
    @ApiModelProperty(value = "是否为强制更新的版本")
    private Boolean forceUpdate;

    /**
     * 下载或跳转的地址
     */
    @ApiModelProperty(value = "新版本下载的地址")
    private String url;

    /**
     * 版本备注说明
     */
    @ApiModelProperty(value = "版本更新说明")
    private String remark;
}
