package com.eghm.logistics.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.AlarmService;
import com.eghm.common.JsonService;
import com.eghm.configuration.SystemProperties;
import com.eghm.logistics.service.ExpressService;
import com.eghm.mapper.ExpressLogisticsMapper;
import com.eghm.model.ExpressLogistics;
import com.eghm.vo.business.order.item.ExpressVO;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.kuaidi100.sdk.api.QueryTrack;
import com.kuaidi100.sdk.api.Subscribe;
import com.kuaidi100.sdk.contant.ApiInfoConstant;
import com.kuaidi100.sdk.core.IBaseClient;
import com.kuaidi100.sdk.pojo.HttpResult;
import com.kuaidi100.sdk.request.*;
import com.kuaidi100.sdk.response.SubscribePushData;
import com.kuaidi100.sdk.utils.SignUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
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

    private static final int OK = 200;

    private final JsonService jsonService;

    private final AlarmService alarmService;

    private final SystemProperties systemProperties;

    private final ExpressLogisticsMapper expressLogisticsMapper;

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
        if (execute.getStatus() != OK) {
            log.error("快递信息查询失败 [{}] [{}] [{}] [{}]", expressNo, expressCode, phone, execute.getError());
            return Lists.newArrayList();
        }
        String body = execute.getBody();
        JSONObject jsonObject = JSON.parseObject(body);
        int status = jsonObject.getIntValue("status");
        if (status != OK) {
            log.error("快递信息查询结果失败 [{}] [{}] [{}] [{}]", expressNo, expressCode, phone, body);
            return Lists.newArrayList();
        }
        return jsonObject.getJSONArray("data").toJavaList(ExpressVO.class);
    }

    @Async
    @Override
    public void subscribe(String expressNo, String expressCode, String phone) {
        SubscribeParam param = new SubscribeParam();
        param.setCompany(expressCode);
        param.setNumber(expressNo);
        param.setKey(systemProperties.getExpress().getKey());
        SubscribeParameters parameters = new SubscribeParameters();
        parameters.setPhone(phone);
        parameters.setCallbackurl(systemProperties.getExpress().getCallback());
        parameters.setSalt(systemProperties.getExpress().getSalt());
        SubscribeReq subscribeReq = new SubscribeReq();
        subscribeReq.setSchema(ApiInfoConstant.SUBSCRIBE_SCHEMA);
        subscribeReq.setParam(new Gson().toJson(param));
        IBaseClient baseClient = new Subscribe();
        try {
            HttpResult result = baseClient.execute(subscribeReq);
            log.info("快递单号订阅成功 [{}] [{}]", expressNo, result);
        } catch (Exception e) {
            log.error("快递单号订阅异常 [{}]", expressNo, e);
            alarmService.sendMsg(String.format("快递单号订阅失败,单号:%s, 快递公司编号:%s, 手机号:%s", expressNo, expressCode, phone));
        }
    }

    @Override
    public void updateExpress(String expressNo, List<SubscribePushData> dataList) {
        List<ExpressVO> voList = dataList.stream().map(data -> new ExpressVO(data.getTime(), data.getContext())).toList();
        LambdaUpdateWrapper<ExpressLogistics> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ExpressLogistics::getExpressNo, expressNo);
        wrapper.set(ExpressLogistics::getContent, jsonService.toJson(voList));
        expressLogisticsMapper.update(wrapper);
    }
}
