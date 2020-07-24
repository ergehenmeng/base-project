package com.eghm.controller.system;

import com.eghm.BaseTest;
import com.eghm.model.dto.system.department.DepartmentAddRequest;
import com.eghm.service.system.SystemDepartmentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class DepartmentControllerTest extends BaseTest {

  @Autowired private SystemDepartmentService systemDepartmentService;

  @Test
  public void add() {
    DepartmentAddRequest request = new DepartmentAddRequest();
    request.setParentCode("100");
    request.setTitle("我是一级部门第二");
    request.setRemark("我是个备注");
    systemDepartmentService.addDepartment(request);
  }
}
