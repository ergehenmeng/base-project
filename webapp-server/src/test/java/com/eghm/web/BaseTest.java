package com.eghm.web;

import cn.hutool.crypto.digest.MD5;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.login.LoginTokenVO;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.Map;

/**
 * @author 二哥很猛
 * @date 2019/11/21 15:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebappApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {

    private HttpHeaders headers = new HttpHeaders();

    protected Map<String,Object> params = Maps.newHashMapWithExpectedSize(10);

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Before
    public void before(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        headers.add("Channel","ANDROID");
        headers.add("Version","v1.2.8");
        headers.add("Os-Version","8.0.1");
        headers.add("Device-Brand","小米");
        headers.add("Device-Model","xiaomi 10 pro");
    }



    public String post(String url){
        String content = this.doPost(url, gson.toJson(params));
        System.out.println("响应结果: " + content);
        return content;
    }

    private String doPost(String url, String content){
        try {
            return mockMvc.perform(MockMvcRequestBuilders.post(URI.create(url)).
                    contentType(MediaType.APPLICATION_JSON_VALUE)
                    .headers(headers)
                    .content(gson.toJson(params)))
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 账号登陆
     *
     * @param account 账号
     * @param password 密码
     */
    public void loginByAccount(String account, String password) {
        params.put("account",account);
        params.put("pwd", MD5.create().digestHex16(password));
        String content = this.post("/api/login/account");
        Type type = new TypeToken<RespBody<LoginTokenVO>>(){}.getType();
        RespBody<LoginTokenVO> json = gson.fromJson(content, type);
        LoginTokenVO data = json.getData();
        headers.add("Token",data.getToken());
        headers.add("Refresh-Token", data.getRefreshToken());
        params.clear();
    }
}
