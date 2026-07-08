package com.eghm.query.business;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.business.model.LoginDevice;
import com.eghm.dto.business.member.LoginLogQueryRequest;
import com.eghm.mapper.LoginDeviceMapper;
import com.eghm.mapper.LoginLogMapper;
import com.eghm.po.LoginDevicePO;
import com.eghm.po.LoginLogPO;
import com.eghm.service.business.LoginQueryGateway;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.member.LoginLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * MyBatis adapter for login read models.
 */
@Repository
@AllArgsConstructor
public class MybatisLoginQueryGateway implements LoginQueryGateway {

    private final LoginLogMapper loginLogMapper;

    private final LoginDeviceMapper loginDeviceMapper;

    @Override
    public Page<LoginLogResponse> listLoginLog(LoginLogQueryRequest request) {
        LambdaQueryWrapper<LoginLogPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LoginLogPO::getMemberId, request.getMemberId());
        wrapper.eq(isNotBlank(request.getChannel()), LoginLogPO::getChannel, request.getChannel());
        wrapper.ge(request.getStartDate() != null, LoginLogPO::getCreateTime, request.getStartDate());
        wrapper.le(request.getEndDate() != null, LoginLogPO::getCreateTime, request.getEndDate());
        wrapper.orderByDesc(LoginLogPO::getId);
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<LoginLogPO> page = loginLogMapper.selectPage(MybatisPageUtil.toMybatis(request.createPage()), wrapper);
        Page<LoginLogResponse> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        result.setRecords(DataUtil.copy(page.getRecords(), LoginLogResponse.class));
        return result;
    }

    @Override
    public List<LoginDevice> listDeviceByMemberId(Long memberId) {
        LambdaQueryWrapper<LoginDevicePO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LoginDevicePO::getMemberId, memberId);
        wrapper.orderByDesc(LoginDevicePO::getId);
        return DataUtil.copy(loginDeviceMapper.selectList(wrapper), LoginDevice.class);
    }
}

