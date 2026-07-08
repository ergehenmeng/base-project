package com.eghm.service.sys;

import com.eghm.dto.sys.dict.DictQueryRequest;
import com.eghm.vo.sys.dict.DictResponse;

import java.util.List;

/**
 * 数据字典查询网关
 *
 * @author 二哥很猛
 */
public interface SysDictQueryGateway {

    List<DictResponse> getList(DictQueryRequest request);
}
