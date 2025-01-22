package com.eghm.service.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.member.tag.MemberTagAddRequest;
import com.eghm.dto.member.tag.MemberTagEditRequest;
import com.eghm.dto.member.tag.MemberTagQueryRequest;
import com.eghm.model.MemberTag;

/**
 * <p>
 * 会员标签 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-03-06
 */
public interface MemberTagService {

    /**
     * 分页查询会员标签信息
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<MemberTag> getByPage(MemberTagQueryRequest request);

    /**
     * 添加标签信息
     *
     * @param request 标签信息
     */
    void create(MemberTagAddRequest request);

    /**
     * 修改标签信息
     *
     * @param request 标签信息
     */
    void update(MemberTagEditRequest request);

    /**
     * 刷新标签信息信息
     *
     * @param id id
     */
    void refresh(Long id);

    /**
     * 刷新标签信息信息
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 查询标签信息
     *
     * @param id id
     * @return 会员标签
     */
    MemberTag selectByIdRequired(Long id);
}
