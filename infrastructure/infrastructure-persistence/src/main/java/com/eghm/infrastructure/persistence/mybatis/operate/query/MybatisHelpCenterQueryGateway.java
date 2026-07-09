package com.eghm.infrastructure.persistence.mybatis.operate.query;

import com.eghm.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.dto.operate.help.HelpQueryDTO;
import com.eghm.dto.operate.help.HelpQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.HelpCenterMapper;
import com.eghm.infrastructure.persistence.mybatis.po.HelpCenterPO;
import com.eghm.application.operate.service.HelpCenterQueryGateway;
import com.eghm.utils.DataUtil;
import com.eghm.vo.operate.help.HelpCenterVO;
import com.eghm.vo.operate.help.HelpResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 帮助中心 MyBatis 查询适配器
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisHelpCenterQueryGateway implements HelpCenterQueryGateway {

    private final HelpCenterMapper helpCenterMapper;

    @Override
    public Page<HelpResponse> getByPage(Page<HelpResponse> page, HelpQueryRequest request) {
        return MybatisPageUtil.fromMybatis(helpCenterMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }

    @Override
    public List<HelpCenterVO> list(HelpQueryDTO dto) {
        List<HelpCenterPO> list = helpCenterMapper.getList(dto.getHelpType(), dto.getQueryName());
        return DataUtil.copy(list, HelpCenterVO.class);
    }
}





