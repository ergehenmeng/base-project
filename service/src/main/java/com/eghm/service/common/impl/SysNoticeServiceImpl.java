package com.eghm.service.common.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constants.ConfigConstant;
import com.eghm.constants.DictConstant;
import com.eghm.dao.mapper.SysNoticeMapper;
import com.eghm.dao.model.SysNotice;
import com.eghm.model.dto.notice.NoticeAddRequest;
import com.eghm.model.dto.notice.NoticeEditRequest;
import com.eghm.model.dto.notice.NoticeQueryRequest;
import com.eghm.model.vo.notice.TopNoticeVO;
import com.eghm.service.common.SysNoticeService;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.service.sys.SysDictService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/22 11:41
 */
@Service("sysNoticeService")
@AllArgsConstructor
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
        return DataUtil.convert(noticeList, notice -> {
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
        wrapper.eq(request.getClassify() != null, SysNotice::getClassify, request.getClassify());
        wrapper.last("order by update_time desc, id desc ");
        return sysNoticeMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public SysNotice getById(Long id) {
        return sysNoticeMapper.selectById(id);
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
