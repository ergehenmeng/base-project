package com.eghm.controller.sys;

import com.eghm.BaseTest;
import com.eghm.model.dto.sys.department.DepartmentAddRequest;
import com.eghm.service.sys.SysDeptService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DeptControllerTest extends BaseTest {

  @Autowired private SysDeptService sysDeptService;

  @Test
  public void add() {
    DepartmentAddRequest request = new DepartmentAddRequest();
    request.setParentCode("100");
    request.setTitle("我是一级部门第二");
    request.setRemark("我是个备注");
    sysDeptService.addDepartment(request);
  }
}
