package com.eghm.infrastructure.persistence.mybatis.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.domain.shared.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 短信发送记录表
 *
 * @author 二哥很猛
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sms_log")
public class SmsLogPO {
    /** id主键 */
    private Long id;

    /** 添加时间 */
    private LocalDateTime createTime;

    /** 短信分类 */
    private TemplateType templateType;

    /** 手机号 */
    private String mobile;

    /** 短信内容 */
    private String content;

    /** 发送状态 0:失败 1:已发送 */
    private Integer state;

}
