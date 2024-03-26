package com.eghm.web.controller;

import com.eghm.service.sys.SysMenuService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.menu.MenuResponse;
import com.eghm.vo.sys.MenuExportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author wyb
 * @since 2023/4/4
 */
@Api(tags = "导出Excel")
@AllArgsConstructor
@RequestMapping("/manage/export")
@RestController
public class ExportController {

    private final SysMenuService sysMenuService;

    @GetMapping("/exportMenu")
    @ApiOperation("导出菜单列表")
    public void exportMenu(HttpServletResponse response) {
        List<MenuResponse> menuList = sysMenuService.getList();
        Random random = ThreadLocalRandom.current();
        List<MenuExportVO> voList = DataUtil.copy(menuList, sysMenu -> {
            MenuExportVO vo = DataUtil.copy(sysMenu, MenuExportVO.class);
            vo.setMock(MenuExportVO.MockEnum.random(random.nextInt(3)));
            return vo;
        });
        EasyExcelUtil.export(response, "菜单表格", voList, MenuExportVO.class);
    }
}
