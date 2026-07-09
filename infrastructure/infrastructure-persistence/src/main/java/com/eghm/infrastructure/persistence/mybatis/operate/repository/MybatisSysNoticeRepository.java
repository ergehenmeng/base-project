package com.eghm.infrastructure.persistence.mybatis.operate.repository;

import com.eghm.infrastructure.persistence.mybatis.mapper.SysNoticeMapper;
import com.eghm.domain.operate.model.SysNotice;
import com.eghm.domain.operate.repository.SysNoticeRepository;
import com.eghm.infrastructure.persistence.mybatis.po.SysNoticePO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisSysNoticeRepository implements SysNoticeRepository {

    private final SysNoticeMapper sysNoticeMapper;

    @Override
    public SysNotice findById(Long id) {
        return DataUtil.copy(sysNoticeMapper.selectById(id), SysNotice.class);
    }

    @Override
    public void save(SysNotice notice) {
        sysNoticeMapper.insert(DataUtil.copy(notice, SysNoticePO.class));
    }

    @Override
    public void update(SysNotice notice) {
        sysNoticeMapper.updateById(DataUtil.copy(notice, SysNoticePO.class));
    }

    @Override
    public void deleteById(Long id) {
        sysNoticeMapper.deleteById(id);
    }

}
