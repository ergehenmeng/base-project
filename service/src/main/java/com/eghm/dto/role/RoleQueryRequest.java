package com.eghm.dto.role;

import com.eghm.dto.ext.PagingQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色查询
 * @author 二哥很猛
 * @date 2018/11/26 15:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleQueryRequest extends PagingQuery {
}
