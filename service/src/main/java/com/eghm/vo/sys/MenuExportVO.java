package com.eghm.vo.sys;

import com.alibaba.excel.annotation.ExcelProperty;
import com.eghm.annotation.ExcelDesc;
import com.eghm.annotation.ExcelDict;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author wyb
 * @since 2023/4/4
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuExportVO extends ExcelStyle {

    @ApiModelProperty("id主键")
    @ExcelProperty("ID")
    private String id;

    @ApiModelProperty("菜单名称")
    @ExcelProperty("菜单名称")
    private String title;

    @ApiModelProperty("菜单标示符")
    @ExcelProperty("菜单标示符")
    private String code;

    @ApiModelProperty("父节点ID,一级菜单默认为0")
    @ExcelProperty("父节点ID")
    private String pid;

    @ApiModelProperty("菜单地址")
    @ExcelProperty("菜单地址")
    private String path;

    // 前提需要在sys_dict中定义menu_grade
    @ApiModelProperty("菜单级别 1:导航菜单 2:按钮菜单")
    @ExcelDict("menu_grade")
    @ExcelProperty("菜单级别")
    private Integer grade;

    @ApiModelProperty("模拟枚举字段")
    @ExcelProperty(value = "枚举", converter = EnumExcelConverter.class)
    private MockEnum mock;

    @Getter
    @AllArgsConstructor
    public enum MockEnum {

        INIT(0, "初始化"),

        START(1, "开始"),

        END(2, "结束");

        private final int value;

        @ExcelDesc
        private final String name;

        public static MockEnum random(int index) {
            return MockEnum.values()[index];
        }
    }
}
