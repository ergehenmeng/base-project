package com.eghm.business.operation.support.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.business.operation.support.dto.HelpAddRequest;
import com.eghm.business.operation.support.dto.HelpEditRequest;
import com.eghm.business.operation.support.dto.HelpQueryDTO;
import com.eghm.business.operation.support.dto.HelpQueryRequest;
import com.eghm.business.operation.support.entity.HelpCenter;
import com.eghm.business.operation.support.vo.HelpCenterVO;
import com.eghm.business.operation.support.vo.HelpResponse;

import java.util.List;

/**
 * @author 帮助说明
 * @since 2018/11/20 20:20
 */
public interface HelpCenterService {

    /**
     * 分页获取帮助说明
     *
     * @param request 前台参数
     * @return 分页列表
     */
    Page<HelpResponse> getByPage(HelpQueryRequest request);

    /**
     * 添加帮助说明
     *
     * @param request 前台参数
     */
    void create(HelpAddRequest request);

    /**
     * 更新帮助说明
     *
     * @param request 前台参数
     */
    void update(HelpEditRequest request);

    /**
     * 排序
     *
     * @param id     id
     * @param sortBy 排序 最大999
     */
    void sortBy(Long id, Integer sortBy);

    /**
     * 删除帮助说明
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 根据id查询
     *
     * @param id id
     * @return 帮助说明
     */
    HelpCenter selectById(Long id);

    /**
     * 按分类查询帮助信息
     *
     * @param dto 查询条件
     * @return 不分页
     */
    List<HelpCenterVO> list(HelpQueryDTO dto);
}

