package com.eghm.repository.operate;

import com.eghm.mapper.SysNoticeMapper;
import com.eghm.operate.model.SysNotice;
import com.eghm.operate.repository.SysNoticeRepository;
import com.eghm.po.SysNoticePO;
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

    @Override
    public void publish(Long id) {
        SysNoticePO notice = new SysNoticePO();
        notice.setState(SysNoticePO.STATE_1);
        notice.setId(id);
        sysNoticeMapper.updateById(notice);
    }

    @Override
    public void cancelPublish(Long id) {
        SysNoticePO notice = new SysNoticePO();
        notice.setState(SysNoticePO.STATE_0);
        notice.setId(id);
        sysNoticeMapper.updateById(notice);
    }
}
