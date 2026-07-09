package com.eghm.interfaces.webapp.controller;

import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.domain.system.model.SysDictItem;
import com.eghm.application.system.service.SysDictApplicationService;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.sys.dict.BaseItemVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/3/11
 */

@RestController
@Tag(name = "数据字典")
@AllArgsConstructor
@RequestMapping(value = "/webapp/dict", produces = MediaType.APPLICATION_JSON_VALUE)
public class DictController {

    private final SysDictApplicationService sysDictService;

    @GetMapping("/list")
    @Operation(summary = "列表")
    @Parameter(name = "nid", description = "数据字典编号", required = true)
    public RespBody<List<BaseItemVO>> list(@RequestParam("nid") String nid) {
        List<SysDictItem> byPage = sysDictService.getDictByNid(nid);
        return RespBody.success(DataUtil.copy(byPage, BaseItemVO.class));
    }
}
