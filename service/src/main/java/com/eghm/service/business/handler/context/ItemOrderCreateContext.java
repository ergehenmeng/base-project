package com.eghm.service.business.handler.context;

import com.eghm.annotation.Sign;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.service.business.handler.dto.BaseItemDTO;
import com.eghm.service.business.handler.dto.VisitorVO;
import com.eghm.validation.annotation.Mobile;
import com.eghm.state.machine.Context;
import com.eghm.validation.group.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/27
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ItemOrderCreateContext extends AsyncKey implements Context {

    @ApiModelProperty("商品信息(例如门票id,餐饮券id,房型id,商品id,线路id)")
    @Size(min = 1, max = 99, message = "商品不能超过99种", groups = {TicketOrderCreateGroup.class, ItemOrderCreateGroup.class, RestaurantOrderCreateGroup.class, LineOrderCreateGroup.class, HomestayOrderCreateGroup.class})
    private List<BaseItemDTO> itemList;

    @Sign
    @ApiModelProperty(hidden = true, value = "用户id")
    private Long userId;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("联系人电话")
    @Mobile(message = "联系人手机号格式错误", groups = {TicketOrderCreateGroup.class, ItemOrderCreateGroup.class, RestaurantOrderCreateGroup.class, LineOrderCreateGroup.class, HomestayOrderCreateGroup.class})
    private String mobile;

    @ApiModelProperty("联系人姓名")
    @Size(min = 2, max = 10, message = "联系人姓名应为2~10字符", groups = {TicketOrderCreateGroup.class, ItemOrderCreateGroup.class, RestaurantOrderCreateGroup.class, LineOrderCreateGroup.class, HomestayOrderCreateGroup.class})
    @NotBlank(message = "联系人姓名不能为空", groups = {TicketOrderCreateGroup.class, ItemOrderCreateGroup.class, RestaurantOrderCreateGroup.class, LineOrderCreateGroup.class, HomestayOrderCreateGroup.class})
    private String nickName;

    @ApiModelProperty("游客信息列表")
    @Size(min = 1, max = 99, message = "游客人数不能超过99人", groups = {LineOrderCreateGroup.class, HomestayOrderCreateGroup.class})
    private List<VisitorVO> visitorList;

    @ApiModelProperty("开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空", groups = {HomestayOrderCreateGroup.class})
    private LocalDate startDate;

    @ApiModelProperty("截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "截止日期不能为空", groups = {HomestayOrderCreateGroup.class})
    private LocalDate endDate;

    @ApiModelProperty("出行日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "出行日期不能为空", groups = {LineOrderCreateGroup.class})
    private LocalDate configDate;

    /**
     * 获取下单的第一个产品信息, 针对绝多数品类,只支持单商品下单, 只有普通商品支持多品类或多店铺同时下单
     * @return 商品信息
     */
    public BaseItemDTO getFirstProduct(){
        return itemList.get(0);
    }

    @ApiModelProperty("源状态")
    private Integer from;

    @ApiModelProperty("新状态")
    private Integer to;

    @Override
    public void setFrom(Integer from) {
        this.from = from;
    }

    @Override
    public void setTo(Integer to) {
        this.to = to;
    }

    @Override
    public Integer getFrom() {
        return from;
    }

    @Override
    public Integer getTo() {
        return to;
    }
}
