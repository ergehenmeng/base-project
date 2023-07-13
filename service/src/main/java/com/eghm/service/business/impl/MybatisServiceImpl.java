package com.eghm.service.business.impl;

import com.eghm.mapper.MybatisMapper;
import com.eghm.service.business.MybatisService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2023/7/12
 */
@Service("mybatisService")
@AllArgsConstructor
public class MybatisServiceImpl implements MybatisService {

    private final MybatisMapper mybatisMapper;

    @Override
    public void test() {
        try {
            int updated = mybatisMapper.updateState();
//            if (updated == 1) {
//                throw new BusinessException(ErrorCode.MEMBER_HAS_REFUND);
//            }
        } catch (Exception e) {
            mybatisMapper.updateState2();
            throw new RuntimeException("123123");
        }
    }
}
