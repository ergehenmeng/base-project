package com.eghm.business.operation.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.platform.config.service.SysConfigApi;
import com.eghm.foundation.core.constants.ConfigConstant;
import com.eghm.foundation.core.constants.DictConstant;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.business.operation.delivery.dto.NoticeAddRequest;
import com.eghm.business.operation.delivery.dto.NoticeEditRequest;
import com.eghm.business.operation.delivery.dto.NoticeQueryRequest;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.business.operation.delivery.mapper.SysNoticeMapper;
import com.eghm.business.operation.delivery.entity.SysNotice;
import com.eghm.business.operation.delivery.service.SysNoticeService;
import com.eghm.business.operation.delivery.service.DeliveryCacheService;
import com.eghm.platform.config.service.SysDictService;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.business.operation.delivery.vo.NoticeDetailVO;
import com.eghm.business.operation.delivery.vo.NoticeResponse;
import com.eghm.business.operation.delivery.vo.NoticeTopVO;
import com.eghm.business.operation.delivery.vo.NoticeVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2019/8/22 11:41
 */
@Slf4j
@AllArgsConstructor
@Service("sysNoticeService")
public class SysNoticeServiceImpl implements SysNoticeService {

    private final SysConfigApi sysConfigApi;

    private final SysDictService sysDictService;

    private final SysNoticeMapper sysNoticeMapper;

    private final DeliveryCacheService deliveryCacheService;

    @Override
    public Page<NoticeResponse> getByPage(NoticeQueryRequest request) {
        return sysNoticeMapper.getByPage(request.createPage(), request);
    }

    @Override
    public List<NoticeTopVO> getTop() {
        int noticeLimit = sysConfigApi.getInt(ConfigConstant.NOTICE_LIMIT);
        List<SysNotice> noticeList = deliveryCacheService.getNoticeList(noticeLimit);
        return DataUtil.copy(noticeList, notice -> {
            NoticeTopVO vo = DataUtil.copy(notice, NoticeTopVO.class);
            // 将公告类型包含到标题中 例如 紧急通知: 中印发生小规模冲突
            vo.setTitle(sysDictService.getDictValue(DictConstant.NOTICE_TYPE, notice.getNoticeType()) + ": " + vo.getTitle());
            return vo;
        });
    }

    @Override
    public List<NoticeVO> getList(PagingQuery query) {
        LambdaQueryWrapper<SysNotice> wrapper = Wrappers.lambdaQuery();
        wrapper.select(SysNotice::getId, SysNotice::getTitle, SysNotice::getCoverUrl,  SysNotice::getNoticeType);
        wrapper.eq(SysNotice::getState, true);
        wrapper.orderByDesc(SysNotice::getId);
        Page<SysNotice> selectedPage = sysNoticeMapper.selectPage(query.createPage(false), wrapper);
        return DataUtil.copy(selectedPage.getRecords(), NoticeVO.class);
    }

    @Override
    public void create(NoticeAddRequest request) {
        DataUtil.copy(request, SysNotice.class, sysNoticeMapper::insert);
    }

    @Override
    public void update(NoticeEditRequest request) {
        DataUtil.copy(request, SysNotice.class, sysNoticeMapper::updateById);
    }

    @Override
    public void delete(Long id) {
        sysNoticeMapper.deleteById(id);
    }

    @Override
    public NoticeDetailVO detailById(Long id) {
        SysNotice notice = sysNoticeMapper.selectById(id);
        if (notice == null) {
            log.info("公告信息已删除 [{}]", id);
            throw new BusinessException(ErrorCode.NOTICE_NOT_NULL);
        }
        NoticeDetailVO vo = DataUtil.copy(notice, NoticeDetailVO.class);
        vo.setNoticeType(sysDictService.getDictValue(DictConstant.NOTICE_TYPE, notice.getNoticeType()));
        return vo;
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
    public SysNotice selectById(Long id) {
        return sysNoticeMapper.selectById(id);
    }

    @Override
    public List<NoticeVO> getList(List<Long> ids) {
        return sysNoticeMapper.getList(ids);
    }

    @Override
    public void publish(Long id) {
        SysNotice notice = new SysNotice();
        notice.setState(1);
        notice.setId(id);
        sysNoticeMapper.updateById(notice);
    }

    @Override
    public void cancelPublish(Long id) {
        SysNotice notice = new SysNotice();
        notice.setState(SysNotice.STATE_0);
        notice.setId(id);
        sysNoticeMapper.updateById(notice);
    }
}
