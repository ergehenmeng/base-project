package com.eghm.service.operate;

import com.eghm.dto.ext.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.operate.notice.NoticeQueryRequest;
import com.eghm.vo.operate.notice.NoticeResponse;
import com.eghm.vo.operate.notice.NoticeVO;

import java.util.List;

/**
 * 系统公告查询端口
 *
 * @author 二哥很猛
 */
public interface SysNoticeQueryGateway {

    Page<NoticeResponse> getByPage(Page<NoticeResponse> page, NoticeQueryRequest request);

    List<NoticeVO> getList(PagingQuery query);
}
