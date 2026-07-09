package com.eghm.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.unit.DataSize;

/**
 * 基础设施层常量
 *
 * @author 二哥很猛
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InfraConstant {

    /**
     * 单日上传限制: 默认128M
     */
    public static final DataSize DAY_MAX_UPLOAD = DataSize.ofMegabytes(128);

}
