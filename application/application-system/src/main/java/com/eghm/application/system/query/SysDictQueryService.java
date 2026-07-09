package com.eghm.application.system.query;

import com.eghm.application.shared.dto.sys.dict.DictQueryRequest;
import com.eghm.application.shared.vo.sys.dict.DictResponse;

import java.util.List;

/**
 * 数据字典查询网关
 *
 * @author 二哥很猛
 */
public interface SysDictQueryService {

    List<DictResponse> getList(DictQueryRequest request);
}
