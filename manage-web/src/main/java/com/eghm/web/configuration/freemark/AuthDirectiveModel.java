package com.eghm.web.configuration.freemark;

import cn.hutool.core.collection.CollUtil;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.security.SecurityOperator;
import com.eghm.configuration.security.SecurityOperatorHolder;
import com.eghm.dao.model.SysMenu;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <@auth nid="">html代码</@auth>
 * 按钮权限宏
 *
 * @author 二哥很猛
 * @date 2019/1/18 17:19
 */
@Component
public class AuthDirectiveModel implements TemplateDirectiveModel {

    private static final String NID = "nid";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        Object nidValue = params.get(NID);
        if (nidValue == null) {
            throw new BusinessException(ErrorCode.AUTH_NID_ERROR);
        }
        String nid = nidValue.toString();
        SecurityOperator operator = SecurityOperatorHolder.getRequiredOperator();
        List<SysMenu> menuList = operator.getButtonMenu();
        if (CollUtil.isNotEmpty(menuList)) {
            for (SysMenu menu : menuList) {
                String authNid = menu.getNid();
                if (authNid.equals(nid)) {
                    body.render(env.getOut());
                    return;
                }
            }
        }
    }
}
