package com.eghm.web;

import com.eghm.configuration.SystemProperties;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.login.LoginResponse;
import com.eghm.service.common.JsonService;
import com.eghm.service.common.JwtTokenService;
import com.eghm.web.configuration.filter.AuthFilter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Type;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 二哥很猛
 * @date 2019/11/21 15:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = ManageApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class BaseTest {

    private final HttpHeaders headers = new HttpHeaders();

    protected final Map<String, Object> params = Maps.newHashMapWithExpectedSize(10);

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private JsonService jsonService;

    @Autowired
    private SystemProperties systemProperties;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Before
    public void before() {
        AuthFilter filter = new AuthFilter(systemProperties.getManage(), jwtTokenService);
        filter.exclude(systemProperties.getManage().getSecurity().getIgnore());
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(filter).build();
        this.mockLogin("13000000000", "123456");
    }

    public void mockLogin(String userName, String password) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("userName", userName);
        map.put("pwd", password);
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post(URI.create("/manage/login"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(this.headers)
                        .content(jsonService.toJson(map));
        try {
            mockMvc.perform(builder).andDo(mvcResult -> {
                String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                RespBody<LoginResponse> respBody = jsonService.fromJson(content, new TypeReference<RespBody<LoginResponse>>() {});
                headers.add("token", respBody.getData().getToken());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void get(String url) {
        try {
            mockMvc
                    .perform(
                            MockMvcRequestBuilders.get(URI.create(url))
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                    .headers(headers)
                                    .content(jsonService.toJson(params)))
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            log.error("请求异常", e);
        }
    }

    protected void post(String url) {
        try {
            MockHttpServletRequestBuilder builder =
                    MockMvcRequestBuilders.post(URI.create(url)).contentType(MediaType.APPLICATION_JSON_VALUE).headers(this.headers).content(jsonService.toJson(params));
            mockMvc.perform(builder).andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            log.error("请求异常", e);
        }
    }
}
