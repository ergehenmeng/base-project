package com.eghm.service.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eghm.configuration.SystemProperties;
import com.eghm.service.business.ExpressService;
import com.eghm.vo.business.order.item.ExpressVO;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.kuaidi100.sdk.api.QueryTrack;
import com.kuaidi100.sdk.core.IBaseClient;
import com.kuaidi100.sdk.pojo.HttpResult;
import com.kuaidi100.sdk.request.QueryTrackParam;
import com.kuaidi100.sdk.request.QueryTrackReq;
import com.kuaidi100.sdk.utils.SignUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/10
 */

@Slf4j
@AllArgsConstructor
@Service("expressService")
public class ExpressServiceImpl implements ExpressService {

    private final SystemProperties systemProperties;

    @Override
    public List<ExpressVO> getExpressList(String expressNo, String expressCode) {
        return this.getExpressList(expressNo, expressCode, null);
    }

    @Override
    public List<ExpressVO> getExpressList(String expressNo, String expressCode, String phone) {
        QueryTrackReq queryTrackReq = new QueryTrackReq();
        QueryTrackParam queryTrackParam = new QueryTrackParam();
        queryTrackParam.setCom(expressCode);
        queryTrackParam.setNum(expressNo);
        queryTrackParam.setPhone(phone);
        String param = new Gson().toJson(queryTrackParam);
        SystemProperties.Express100 express = systemProperties.getExpress();
        queryTrackReq.setParam(param);
        queryTrackReq.setCustomer(express.getCustomer());
        queryTrackReq.setSign(SignUtils.querySign(param, express.getKey(), express.getCustomer()));
        IBaseClient baseClient = new QueryTrack();
        HttpResult execute;
        try {
            execute = baseClient.execute(queryTrackReq);
        } catch (Exception e) {
            log.error("快递查询异常 [{}] [{}] [{}]", expressNo, expressCode, phone, e);
            return Lists.newArrayList();
        }

        if (execute.getStatus() != 200) {
            log.error("快递信息查询失败 [{}] [{}] [{}] [{}]", expressNo, expressCode, phone, execute.getError());
            return Lists.newArrayList();
        }
        String body = execute.getBody();
        JSONObject jsonObject = JSON.parseObject(body);
        int status = jsonObject.getIntValue("status");
        if (status != 200) {
            log.error("快递信息查询结果失败 [{}] [{}] [{}]", expressNo, expressCode, phone);
            return Lists.newArrayList();
        }
        return jsonObject.getJSONArray("data").toJavaList(ExpressVO.class);
    }
}
