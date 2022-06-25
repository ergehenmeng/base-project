package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.HomestayMapper;
import com.eghm.dao.model.Homestay;
import com.eghm.model.dto.homestay.HomestayAddRequest;
import com.eghm.model.dto.homestay.HomestayEditRequest;
import com.eghm.service.business.HomestayService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wyb 2022/6/25 14:09
 */
@Service("homestayService")
@AllArgsConstructor
@Slf4j
public class HomestayServiceImpl implements HomestayService {

    private final HomestayMapper homestayMapper;

    @Override
    public void create(HomestayAddRequest request) {
        this.checkTitleRedo(request.getTitle(), null);
        // TODO 商家id补充
        Homestay homestay = DataUtil.copy(request, Homestay.class);
        homestayMapper.insert(homestay);
    }

    @Override
    public void update(HomestayEditRequest request) {
        this.checkTitleRedo(request.getTitle(), request.getId());
    }

    /**
     * 校验民宿名称是否被占用
     * @param title 民宿名称
     * @param id    id
     */
    private void checkTitleRedo(String title, Long id) {
        LambdaQueryWrapper<Homestay> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Homestay::getTitle, title);
        wrapper.ne(id != null, Homestay::getId, id);
        Integer count = homestayMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("民宿名称被占用 [{}]", title);
            throw new BusinessException(ErrorCode.HOMESTAY_TITLE_REDO);
        }
    }

}
