package com.eghm.service.business.impl;

import com.eghm.configuration.encoder.Encoder;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.mapper.SysMerchantMapper;
import com.eghm.dao.model.SysMerchant;
import com.eghm.model.dto.merchant.MerchantAddRequest;
import com.eghm.model.dto.merchant.MerchantEditRequest;
import com.eghm.model.enums.MerchantState;
import com.eghm.service.business.SysMerchantService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2022/5/27
 */
@Service("sysMerchantService")
@AllArgsConstructor
public class SysMerchantServiceImpl implements SysMerchantService {
    
    private final SysMerchantMapper sysMerchantMapper;
    
    private final SysConfigApi sysConfigApi;
    
    private final Encoder encoder;
    
    @Override
    public void create(MerchantAddRequest request) {
        // TODO 账户名重复校验
        SysMerchant merchant = DataUtil.copy(request, SysMerchant.class);
        String pwd = sysConfigApi.getString(ConfigConstant.MERCHANT_PWD);
        merchant.setPwd(encoder.encode(pwd));
        merchant.setInitPwd(true);
        merchant.setState(MerchantState.NORMAL);
        sysMerchantMapper.insert(merchant);
    
        // TODO 根据商户类型进行授权
    }
    
    @Override
    public void update(MerchantEditRequest request) {
        SysMerchant merchant = DataUtil.copy(request, SysMerchant.class);
        sysMerchantMapper.insert(merchant);
        // TODO 更新角色权限信息
    }
}
