package com.eghm.vo.sys;

import com.alibaba.excel.annotation.ExcelProperty;
import com.eghm.annotation.ExcelDesc;
import com.eghm.annotation.ExcelDict;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "id主键")
    @ExcelProperty("ID")
    private String id;

    @Schema(description = "菜单名称")
    @ExcelProperty("菜单名称")
    private String title;

    @Schema(description = "菜单标示符")
    @ExcelProperty("菜单标示符")
    private String code;

    @Schema(description = "父节点ID,一级菜单默认为0")
    @ExcelProperty("父节点ID")
    private String pid;

    @Schema(description = "菜单地址")
    @ExcelProperty("菜单地址")
    private String path;

    @Schema(description = "菜单级别 1:导航菜单 2:按钮菜单")
    @ExcelDict("menu_grade")
    @ExcelProperty("菜单级别")
    private Integer grade;

    @Schema(description = "模拟枚举字段")
    @ExcelProperty(value = "枚举", converter = EnumExcelConverter.class)
    private MockEnum mock;

    @Getter
    @AllArgsConstructor
    public enum MockEnum {

        /**
         * 初始化
         */
        INIT(0, "初始化"),

        /**
         * 开始
         */
        START(1, "开始"),

        /**
         * 结束
         */
        END(2, "结束");

        private final int value;

        @ExcelDesc
        private final String name;

        public static MockEnum random(int index) {
            return MockEnum.values()[index];
        }
    }
}
