package com.eghm.model.dto.feedback;

import com.eghm.model.dto.ext.ActionRecord;
import lombok.Getter;
import lombok.Setter;

/**
 * 反馈处理
 * @author 二哥很猛
 * @date 2019/8/28 14:26
 */
@Setter
@Getter
public class FeedbackDisposeRequest extends ActionRecord {

    private static final long serialVersionUID = -7275206427146713271L;

    /**
     * id
     */
    private Integer id;

    /**
     * 备注信息
     */
    private String remark;

}
