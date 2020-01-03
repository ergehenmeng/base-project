package com.eghm.freemark;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.model.system.SystemDict;
import com.eghm.service.system.SystemDictService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 数据字典下拉列表渲染
 * <@select id="" name="" class="" title="" nid="" value="" ></@select>
 *
 * @author 二哥很猛
 * @date 2018/11/27 17:25
 */
@Component
public class DictDirectiveModel implements TemplateDirectiveModel {

    @Autowired
    private SystemDictService systemDictService;

    /**
     * select标签id
     */
    private static final String ID = "id";

    /**
     * select标签name
     */
    private static final String NAME = "name";

    /**
     * select标签class
     */
    private static final String CLS = "class";

    /**
     * select标签title
     */
    private static final String TITLE = "title";

    /**
     * system_dict表nid
     */
    private static final String NID = "nid";


    /**
     * select选中的值
     */
    private static final String VALUE = "value";

    /**
     * 是否显示渲染全部 <option value="">全部</option> 默认false
     */
    private static final String TOTAL = "total";

    /**
     * total_value常量
     */
    private static final String TOTAL_VALUE = "true";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws IOException {
        Iterator iterator = params.keySet().iterator();

        StringBuilder builder = new StringBuilder("<select ");
        //默认不显示
        String total = "false";
        //选中的值
        String optionValue = null;
        String nid = null;

        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            Object value = params.get(key);
            if (value == null) {
                continue;
            }
            if (NID.equals(key)) {
                nid = value.toString();
            } else if (NAME.equals(key)) {
                builder.append(" name='").append(value).append("' ");
            } else if (CLS.equals(key)) {
                builder.append(" class='").append(value).append("' ");
            } else if (ID.equals(key)) {
                builder.append(" id='").append(value).append("' ");
            } else if (TITLE.equals(key)) {
                builder.append(" title='").append(value).append("' ");
            } else if (VALUE.equals(key)) {
                optionValue = value.toString();
            } else if (TOTAL.equals(key)) {
                total = value.toString();
            }
        }
        builder.append(">");
        if (nid == null) {
            throw new BusinessException(ErrorCode.NID_IS_NULL);
        }
        StringBuilder renderOption = renderOption(nid, total, optionValue);
        builder.append(renderOption);
        builder.append("</select>");
        Writer out = env.getOut();
        out.write(builder.toString());
    }

    /**
     * 渲染option列
     *
     * @param nid   system_dict表中的nid
     * @param total 是否显示全部
     * @param value 选中值
     * @return option列表
     */
    private StringBuilder renderOption(String nid, String total, String value) {
        List<SystemDict> dictList = systemDictService.getDictByNid(nid);
        StringBuilder builder = new StringBuilder();
        if (TOTAL_VALUE.equals(total)) {
            builder.append("<option value=''>全部</option>");
        }
        if (dictList != null && dictList.size() > 0) {
            for (SystemDict dict : dictList) {
                if (value != null && value.equals(dict.getHiddenValue().toString())) {
                    //<option value='1' selected>类型</option>
                    builder.append("<option value='").append(dict.getHiddenValue()).append("' selected >").append(dict.getShowValue()).append("</option>");
                } else {
                    builder.append("<option value='").append(dict.getHiddenValue()).append("' >").append(dict.getShowValue()).append("</option>");
                }
            }
        }
        return builder;
    }
}
