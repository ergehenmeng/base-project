package com.eghm.model.dto.business.sms;

import com.eghm.model.ext.PagingQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @date 2019/8/21 18:03
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class SmsTemplateQueryRequest extends PagingQuery {

    private static final long serialVersionUID = -1036182177988776422L;

}
