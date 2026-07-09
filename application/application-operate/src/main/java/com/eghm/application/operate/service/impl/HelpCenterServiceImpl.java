package com.eghm.application.operate.service.impl;

import com.eghm.dto.ext.Page;
import com.eghm.dto.operate.help.HelpAddRequest;
import com.eghm.dto.operate.help.HelpEditRequest;
import com.eghm.dto.operate.help.HelpQueryDTO;
import com.eghm.dto.operate.help.HelpQueryRequest;
import com.eghm.domain.operate.model.HelpCenter;
import com.eghm.domain.operate.repository.HelpCenterRepository;
import com.eghm.application.operate.service.HelpCenterQueryGateway;
import com.eghm.application.operate.service.HelpCenterService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.operate.help.HelpCenterVO;
import com.eghm.vo.operate.help.HelpResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/11/20 20:20
 */
@AllArgsConstructor
@Service("helpCenterService")
public class HelpCenterServiceImpl implements HelpCenterService {

    private final HelpCenterRepository helpCenterRepository;

    private final HelpCenterQueryGateway helpCenterQueryGateway;

    @Override
    public Page<HelpResponse> getByPage(HelpQueryRequest request) {
        return helpCenterQueryGateway.getByPage(request.createPage(), request);
    }

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

    @Override
    public List<HelpCenterVO> list(HelpQueryDTO dto) {
        return helpCenterQueryGateway.list(dto);
    }
}
