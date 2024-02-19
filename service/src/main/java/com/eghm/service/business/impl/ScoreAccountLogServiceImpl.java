package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.account.score.ScoreAccountQueryRequest;
import com.eghm.mapper.ScoreAccountLogMapper;
import com.eghm.service.business.ScoreAccountLogService;
import com.eghm.vo.business.account.ScoreAccountLogResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商户积分变动明细表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-12
 */
@Slf4j
@AllArgsConstructor
@Service("scoreAccountLogService")
public class ScoreAccountLogServiceImpl implements ScoreAccountLogService {

    private final ScoreAccountLogMapper scoreAccountLogMapper;

    @Override
    public Page<ScoreAccountLogResponse> listPage(ScoreAccountQueryRequest request) {
        return scoreAccountLogMapper.listPage(request.createPage(), request);
    }

    @Override
    public List<ScoreAccountLogResponse> getList(ScoreAccountQueryRequest request) {
        return scoreAccountLogMapper.listPage(request.createNullPage(), request).getRecords();
    }
}
