package com.eghm.application.operate.service.impl;

import com.eghm.application.shared.dto.operate.help.HelpAddRequest;
import com.eghm.application.shared.dto.operate.help.HelpEditRequest;
import com.eghm.domain.operate.model.HelpCenter;
import com.eghm.domain.operate.repository.HelpCenterRepository;
import com.eghm.application.operate.service.HelpCenterApplicationService;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2018/11/20 20:20
 */
@AllArgsConstructor
@Service("helpCenterService")
public class HelpCenterApplicationServiceImpl implements HelpCenterApplicationService {

    private final HelpCenterRepository helpCenterRepository;

    @Override
    public void create(HelpAddRequest request) {
        HelpCenter helpCenter = DataUtil.copy(request, HelpCenter.class);
        helpCenterRepository.save(helpCenter);
    }

    @Override
    public void update(HelpEditRequest request) {
        HelpCenter helpCenter = DataUtil.copy(request, HelpCenter.class);
        helpCenterRepository.update(helpCenter);
    }

    @Override
    public void sortBy(Long id, Integer sortBy) {
        helpCenterRepository.updateSort(id, sortBy);
    }

    @Override
    public void delete(Long id) {
        helpCenterRepository.deleteById(id);
    }

    @Override
    public HelpCenter selectById(Long id) {
        return helpCenterRepository.findById(id);
    }
}
