package com.eghm.dto.ext;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/28 17:59
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilePath implements Serializable {

    private static final long serialVersionUID = -8140748928381521323L;

    @ApiModelProperty("文件相对路径")
    private String path;

    @ApiModelProperty("ip+端口号")
    private String address;

    @ApiModelProperty("大小")
    private Long size;

    @ApiModelProperty("文件相对路径列表")
    private List<String> paths;
}
