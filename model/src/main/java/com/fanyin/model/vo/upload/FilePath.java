package com.fanyin.model.vo.upload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@Getter
@Setter
public class FilePath implements Serializable {

    private static final long serialVersionUID = -8140748928381521323L;

    /**
     * 文件相对路径
     */
    @ApiModelProperty("文件相对路径")
    private String path;

    /**
     * ip+端口号
     */
    @ApiModelProperty("ip+端口号")
    private String address;

    /**
     * 文件相对路径列表
     */
    @ApiModelProperty("文件相对路径列表")
    private List<String> paths;
}
