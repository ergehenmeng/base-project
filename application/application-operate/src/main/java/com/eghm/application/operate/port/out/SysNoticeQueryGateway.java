package com.eghm.application.operate.port.out;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.dto.operate.notice.NoticeQueryRequest;
import com.eghm.application.shared.vo.operate.notice.NoticeResponse;
import com.eghm.application.shared.vo.operate.notice.NoticeVO;

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
