package com.eghm.model.vo.version;

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
public class VersionVO implements Serializable {

    private static final long serialVersionUID = -1238037314236966606L;

    /**
     * 是否为最新版本
     */
    private Boolean latest;

    /**
     * 版本号
     */
    private String version;

    /**
     * 是否为强制更新版本
     */
    private Boolean forceUpdate;

    /**
     * 下载或跳转的地址
     */
    private Boolean url;

    /**
     * 版本备注说明
     */
    private String remark;
}
