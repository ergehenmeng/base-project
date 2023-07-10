package com.eghm.service.common.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.constants.ConfigConstant;
import com.eghm.constants.DictConstant;
import com.eghm.mapper.SysNoticeMapper;
import com.eghm.model.SysNotice;
import com.eghm.dto.notice.NoticeAddRequest;
import com.eghm.dto.notice.NoticeEditRequest;
import com.eghm.dto.notice.NoticeQueryRequest;
import com.eghm.vo.notice.TopNoticeVO;
import com.eghm.service.common.SysNoticeService;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.service.sys.SysDictService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/22 11:41
 */
@Service("sysNoticeService")
@AllArgsConstructor
@Slf4j
public class SysNoticeServiceImpl implements SysNoticeService {

    private final SysNoticeMapper sysNoticeMapper;

    private final CacheProxyService cacheProxyService;

    private final SysDictService sysDictService;

    private final SysConfigApi sysConfigApi;

    @Override
    public List<TopNoticeVO> getList() {
        int noticeLimit = sysConfigApi.getInt(ConfigConstant.NOTICE_LIMIT);
        List<SysNotice> noticeList = cacheProxyService.getNoticeList(noticeLimit);
        SysDictService finalProxy = this.sysDictService;
        return DataUtil.copy(noticeList, notice -> {
            TopNoticeVO vo = DataUtil.copy(notice, TopNoticeVO.class);
            // 将公告类型包含到标题中 例如 紧急通知: 中印发生小规模冲突
            vo.setTitle(finalProxy.getDictValue(DictConstant.NOTICE_CLASSIFY, notice.getClassify()) + ": " + vo.getTitle());
            return vo;
        });
    }

    @Override
    public void create(NoticeAddRequest request) {
        SysNotice notice = DataUtil.copy(request, SysNotice.class);
        sysNoticeMapper.insert(notice);
    }

    @Override
    public void update(NoticeEditRequest request) {
        SysNotice notice = DataUtil.copy(request, SysNotice.class);
        sysNoticeMapper.updateById(notice);
    }

    @Override
    public void delete(Long id) {
        sysNoticeMapper.deleteById(id);
    }

    @Override
    public Page<SysNotice> getByPage(NoticeQueryRequest request) {
        LambdaQueryWrapper<SysNotice> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), SysNotice::getTitle, request.getQueryName());
        wrapper.eq(request.getClassify() != null, SysNotice::getClassify, request.getClassify());
        wrapper.last("order by update_time desc, id desc ");
        return sysNoticeMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public SysNotice getById(Long id) {
        return sysNoticeMapper.selectById(id);
    }

    @Override
    public SysNotice getByIdRequired(Long id) {
        SysNotice notice = sysNoticeMapper.selectById(id);
        if (notice == null) {
            log.info("公告信息未查询到 [{}]", id);
            throw new BusinessException(ErrorCode.NOTICE_NOT_FOUND);
        }
        return notice;
    }

    @Override
    public void publish(Long id) {
        cacheProxyService.publishNotice(id);
    }

    @Override
    public void cancelPublish(Long id) {
        cacheProxyService.cancelPublishNotice(id);
    }
}
