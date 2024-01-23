package com.eghm.service.business;

import com.eghm.dto.statistics.VisitRequest;
import com.eghm.model.MemberVisitLog;
import com.eghm.vo.business.statistics.MemberVisitVO;

import java.util.List;

/**
 * <p>
 * 用户浏览记录表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-23
 */
public interface MemberVisitLogService {

    /**
     * 添加会员浏览记录
     *
     * @param memberVisitLog log
     */
    void insert(MemberVisitLog memberVisitLog);

    /**
     * 获取的浏览记录
     *
     * @param request 查询条件
     * @return 访问记录
     */
    List<MemberVisitVO> dayVisit(VisitRequest request);
}
