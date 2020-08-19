package com.eghm.controller.sys;

import com.eghm.BaseTest;
import com.eghm.model.dto.sys.dept.DeptAddRequest;
import com.eghm.service.sys.SysDeptService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;

public class DeptControllerTest extends BaseTest {

  @Autowired private SysDeptService sysDeptService;



  @Test
  public void add() {
    DeptAddRequest request = new DeptAddRequest();
    request.setParentCode("100");
    request.setTitle("我是一级部门第二");
    request.setRemark("我是个备注");
    sysDeptService.addDepartment(request);
  }

  @Test
  @WithUserDetails(value = "13000000000", userDetailsServiceBeanName = "userDetailsService")
  public void test() {
    super.get("/test");
  }
}
