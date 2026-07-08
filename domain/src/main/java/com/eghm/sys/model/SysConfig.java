package com.eghm.sys.model;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统参数表
 *
 * @author 二哥很猛
 */
@Data
public class SysConfig {

    /** 主键 */
    private Long id;

    /** 参数标示符 */
    private String nid;

    /** 参数名称 */
    private String title;

    /** 参数值 */
    private String content;

    /** 备注信息 */
    private String remark;

    /** 锁定状态(禁止编辑) 0:未锁定,1:锁定 */
    private Boolean locked;

    /** 添加时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /**
     * 校验配置是否允许编辑
     */
    public void assertEditable() {
        if (Boolean.TRUE.equals(locked)) {
            throw new BusinessException(ErrorCode.CONFIG_LOCK_ERROR);
        }
    }

    /**
     * 修改配置内容
     *
     * @param content 配置内容
     */
    public void changeContent(String content) {
        this.content = content;
    }
}
