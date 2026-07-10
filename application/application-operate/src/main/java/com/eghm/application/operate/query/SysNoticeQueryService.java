package com.eghm.application.operate.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.dto.operate.notice.NoticeQueryRequest;
import com.eghm.application.shared.vo.operate.notice.NoticeResponse;
import com.eghm.application.shared.vo.operate.notice.NoticeVO;

import java.util.List;

/**
 * 系统公告查询服务
 *
 * @author 二哥很猛
 */
public interface SysNoticeQueryService {

    Page<NoticeResponse> getByPage(Page<NoticeResponse> page, NoticeQueryRequest request);

    List<NoticeVO> getList(PagingQuery query);
}
