package com.fanyin.dto.system.role;

import com.fanyin.ext.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 角色查询
 * @author 二哥很猛
 * @date 2018/11/26 15:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleQueryRequest extends PageQuery implements Serializable {

    private static final long serialVersionUID = 2824485497219254015L;

}
