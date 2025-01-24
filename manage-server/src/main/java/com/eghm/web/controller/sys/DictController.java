package com.eghm.web.controller.sys;

import com.eghm.annotation.SkipPerm;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.sys.dict.*;
import com.eghm.model.SysDictItem;
import com.eghm.service.sys.SysDictService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.sys.dict.BaseDictResponse;
import com.eghm.vo.sys.dict.BaseItemVO;
import com.eghm.vo.sys.dict.DictResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2019/1/14 11:10
 */
@RestController
@Tag(name = "数据字典管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/dict", produces = MediaType.APPLICATION_JSON_VALUE)
public class DictController {

    private final SysDictService sysDictService;

    @GetMapping("/list")
    @Operation(summary = "列表")
    public RespBody<List<DictResponse>> list(@ParameterObject DictQueryRequest request) {
        List<DictResponse> byPage = sysDictService.getList(request);
        return RespBody.success(byPage);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@Validated @RequestBody DictAddRequest request) {
        sysDictService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody DictEditRequest request) {
        sysDictService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        sysDictService.delete(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/item/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "选项新增")
    public RespBody<Void> itemCreate(@Validated @RequestBody DictItemAddRequest request) {
        sysDictService.itemCreate(request);
        return RespBody.success();
    }

    @PostMapping(value = "/item/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "选项编辑")
    public RespBody<Void> itemUpdate(@Validated @RequestBody DictItemEditRequest request) {
        sysDictService.itemUpdate(request);
        return RespBody.success();
    }

    @PostMapping(value = "/item/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "选项删除")
    public RespBody<Void> itemDelete(@Validated @RequestBody IdDTO dto) {
        sysDictService.itemDelete(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/itemList")
    @Operation(summary = "查询数据字典")
    @SkipPerm
    @Parameter(name = "nidList", description = "字典编码(数组)", example = "a,b,c", required = true, array = @ArraySchema(schema = @Schema(type = "string")))
    public RespBody<List<BaseDictResponse>> itemList(@RequestParam("nidList") List<String> nidList) {
        List<BaseDictResponse> responseList = new ArrayList<>(8);
        for (String nid : nidList) {
            List<SysDictItem> dictList = sysDictService.getDictByNid(nid);
            List<BaseItemVO> itemList = DataUtil.copy(dictList, BaseItemVO.class);
            BaseDictResponse response = new BaseDictResponse();
            response.setItemList(itemList);
            response.setNid(nid);
            responseList.add(response);
        }
        return RespBody.success(responseList);
    }
}
