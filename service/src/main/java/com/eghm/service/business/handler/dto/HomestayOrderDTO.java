package com.eghm.service.business.handler.dto;

import com.eghm.dao.model.HomestayRoom;
import com.eghm.dao.model.HomestayRoomConfig;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/22
 */
@Data
public class HomestayOrderDTO {

    /**
     * 房型信心
     */
    private HomestayRoom homestayRoom;

    /**
     * 房型配置信息
     */
    private List<HomestayRoomConfig> configList;
}
