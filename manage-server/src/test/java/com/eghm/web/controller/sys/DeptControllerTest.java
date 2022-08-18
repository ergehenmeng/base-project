package com.eghm.web.controller.sys;

import com.eghm.model.dto.dept.DeptAddRequest;
import com.eghm.service.sys.SysDeptService;
import com.eghm.web.BaseTest;
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
    sysDeptService.create(request);
  }


}
