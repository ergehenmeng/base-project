package com.fanyin.model.dto.common.image;

import com.fanyin.model.ext.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/28 15:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ImageQueryRequest extends PageQuery implements Serializable {

    private static final long serialVersionUID = 4515347312371743977L;

    private Byte classify;

}
