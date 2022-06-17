package com.eghm.web.controller;

import com.eghm.dao.model.Scenic;
import com.eghm.service.business.ScenicService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyb 2022/6/17 19:06
 */
@RestController
@Api(tags = "景区")
@AllArgsConstructor
@RequestMapping("/scenic")
public class ScenicController {

    private final ScenicService scenicService;

}
