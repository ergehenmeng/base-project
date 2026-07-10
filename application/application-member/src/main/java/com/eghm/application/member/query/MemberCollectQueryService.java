package com.eghm.application.member.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.business.collect.CollectQueryDTO;
import com.eghm.application.shared.dto.business.statistics.CollectRequest;
import com.eghm.domain.shared.enums.CollectType;
import com.eghm.application.shared.vo.business.collect.MemberCollectVO;
import com.eghm.application.shared.vo.business.statistics.CollectStatisticsVO;

import java.util.List;

/**
 * 会员收藏查询服务
 *
 * @author 二哥很猛
 * @since 2024-01-11
 */
public interface MemberCollectQueryService {

    Page<MemberCollectVO> getByPage(Page<MemberCollectVO> page, CollectQueryDTO query);

    List<MemberCollectVO> listCollectedPage(CollectQueryDTO query);

    List<CollectStatisticsVO> dayCollectStatistics(CollectRequest request);

    boolean existsCollectObject(Long collectId, CollectType collectType);
}
