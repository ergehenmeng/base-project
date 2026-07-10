package com.eghm.application.operate.service;

import com.eghm.application.shared.dto.operate.help.HelpAddRequest;
import com.eghm.application.shared.dto.operate.help.HelpEditRequest;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.domain.operate.model.HelpCenter;
import com.eghm.domain.operate.repository.HelpCenterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 帮助说明
 * @since 2018/11/20 20:20
 */
@Service
@AllArgsConstructor
public class HelpCenterApplicationService {

    private final HelpCenterRepository helpCenterRepository;

    /**
     * 添加帮助说明
     *
     * @param request 前台参数
     */
    public void create(HelpAddRequest request) {
        HelpCenter helpCenter = DataUtil.copy(request, HelpCenter.class);
        helpCenterRepository.save(helpCenter);
    }

    /**
     * 更新帮助说明
     *
     * @param request 前台参数
     */
    public void update(HelpEditRequest request) {
        HelpCenter helpCenter = DataUtil.copy(request, HelpCenter.class);
        helpCenterRepository.update(helpCenter);
    }

    /**
     * 排序
     *
     * @param id     id
     * @param sortBy 排序 最大999
     */
    public void sortBy(Long id, Integer sortBy) {
        helpCenterRepository.updateSort(id, sortBy);
    }

    /**
     * 删除帮助说明
     *
     * @param id id
     */
    public void delete(Long id) {
        helpCenterRepository.deleteById(id);
    }

    /**
     * 根据id查询
     *
     * @param id id
     * @return 帮助说明
     */
    public HelpCenter selectById(Long id) {
        return helpCenterRepository.findById(id);
    }
}
