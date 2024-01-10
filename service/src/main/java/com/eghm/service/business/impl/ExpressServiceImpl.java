package com.eghm.service.business.impl;

import com.eghm.service.business.ExpressService;
import com.eghm.vo.business.order.item.ExpressVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/10
 */

@Slf4j
@AllArgsConstructor
@Service("expressService")
public class ExpressServiceImpl implements ExpressService {

    @Override
    public List<ExpressVO> getExpressList(String expressNo, String expressCode) {
        // TODO 待对接第三方
        return null;
    }

    @Override
    public List<ExpressVO> getExpressList(String expressNo, String expressCode, String phone) {
        // TODO 待对接第三方
        return null;
    }
}
