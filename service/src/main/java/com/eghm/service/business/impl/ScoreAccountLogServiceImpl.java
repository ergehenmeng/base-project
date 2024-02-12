package com.eghm.service.business.impl;

import com.eghm.model.ScoreAccountLog;
import com.eghm.mapper.ScoreAccountLogMapper;
import com.eghm.service.business.ScoreAccountLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
