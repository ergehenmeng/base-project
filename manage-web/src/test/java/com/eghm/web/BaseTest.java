package com.eghm.web;

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

import java.net.URI;
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

  private HttpHeaders headers = new HttpHeaders();

  protected Map<String, Object> params = Maps.newHashMapWithExpectedSize(10);

  @Autowired private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @Autowired private Gson gson;

  @Before
  public void before() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }


  protected void get(String url) {
    try {
      mockMvc
          .perform(
              MockMvcRequestBuilders.get(URI.create(url))
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .headers(headers)
                  .content(gson.toJson(params)))
          .andDo(MockMvcResultHandlers.print());
    } catch (Exception e) {
      log.error("请求异常", e);
    }
  }

  protected void post(String url) {
    try {
      MockHttpServletRequestBuilder builder =
          MockMvcRequestBuilders.post(URI.create(url)).contentType(MediaType.APPLICATION_JSON_VALUE).headers(this.headers);
      for (Map.Entry<String, Object> entry : params.entrySet()) {
        builder.param(entry.getKey(), entry.getValue().toString());
      }
      mockMvc.perform(builder).andDo(MockMvcResultHandlers.print());
    } catch (Exception e) {
      log.error("请求异常", e);
    }
  }
}
