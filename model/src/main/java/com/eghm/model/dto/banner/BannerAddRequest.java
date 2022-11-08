package com.eghm.model.dto.banner;

import com.eghm.model.validation.annotation.OptionString;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2019/8/22 19:49
 */
@Data
public class BannerAddRequest implements Serializable {

    private static final long serialVersionUID = -591183099267469325L;

    @ApiModelProperty(value = "标题名称", required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "轮博图类型(数据字典)", required = true)
    @NotNull(message = "轮博图类型不能为空")
    private Byte classify;

    /**
     * 客户端类型 {@link com.eghm.common.enums.Channel}
     */
    @ApiModelProperty(value = "客户端类型 PC, ANDROID, IOS, H5, WECHAT", required = true)
    @OptionString({"IOS", "PC", "ANDROID", "H5", "WECHAT"})
    private String clientType;

    @ApiModelProperty(value = "图片地址", required = true)
    @NotBlank(message = "图片地址不能为空")
    private String imgUrl;

    @ApiModelProperty(value = "点击后跳转的地址")
    private String turnUrl;

    @ApiModelProperty(value = "排序规则 大<->小 最大的在最前面", required = true)
    @NotNull(message = "排序不能为空")
    private Byte sort;

    @ApiModelProperty(value = "开始展示时间(可在指定时间后开始展示)")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty(value = "取消展示的时间(只在某个时间段展示)")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty(value = "是否可点击 true:可以 false:不可以", required = true)
    private Boolean click;

    @ApiModelProperty(value = "备注信息")
    private String remark;

}
