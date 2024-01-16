package com.eghm.dto.ext;

import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;

/**
 * 导出excel通用样式
 *
 * @author wyb
 * @since 2023/3/31
 */

@ColumnWidth(20)
@HeadRowHeight(25)
@ContentRowHeight(20)
@HeadFontStyle(fontHeightInPoints = 12, fontName = "宋体", bold = BooleanEnum.TRUE)
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER, verticalAlignment = VerticalAlignmentEnum.CENTER)
@HeadStyle(fillBackgroundColor = 55, horizontalAlignment = HorizontalAlignmentEnum.CENTER, verticalAlignment = VerticalAlignmentEnum.CENTER)
public class ExcelStyle {
}
