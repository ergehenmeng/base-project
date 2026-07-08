package com.eghm.query.sys;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.net.Ipv4Util;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.mapper.BlackRosterMapper;
import com.eghm.po.BlackRosterPO;
import com.eghm.service.sys.BlackRosterQueryGateway;
import com.eghm.sys.model.BlackRoster;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * MyBatis黑名单查询网关实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisBlackRosterQueryGateway implements BlackRosterQueryGateway {

    private final BlackRosterMapper blackRosterMapper;

    @Override
    public Page<BlackRoster> getByPage(PagingQuery request) {
        LambdaQueryWrapper<BlackRosterPO> wrapper = Wrappers.lambdaQuery();
        if (isNotBlank(request.getQueryName())) {
            if (PatternPool.IPV4.matcher(request.getQueryName()).matches()) {
                long ip = Ipv4Util.ipv4ToLong(request.getQueryName());
                wrapper.ge(BlackRosterPO::getEndIp, ip);
                wrapper.le(BlackRosterPO::getStartIp, ip);
            } else {
                wrapper.like(BlackRosterPO::getRemark, request.getQueryName());
            }
        }
        wrapper.orderByDesc(BlackRosterPO::getId);
        return MybatisPageUtil.copy(blackRosterMapper.selectPage(MybatisPageUtil.toMybatis(request.createPage()), wrapper), BlackRoster.class);
    }
}

