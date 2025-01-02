package com.eghm.vo.sys.ext;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/3
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SysAreaVO {

    @Schema(description = "区域id(唯一)")
    private Long id;

    @Schema(description = "区域名称")
    private String title;

    @Schema(description = "邮编")
    private String zipCode;

    @Schema(description = "地区首字母")
    private String mark;

    @Schema(description = "父节点")
    private Long pid;

    @Schema(description = "子节点")
    private List<SysAreaVO> children;
}
