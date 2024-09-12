package com.eghm.dto.ext;

import com.eghm.enums.MsgType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * websocket消息体内容
 *
 * @author 二哥很猛
 * @since 2024/9/12
 */

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SocketMsg<T> {

    /**
     * 消息类型, 前端根据该类型做不同的处理
     */
    private MsgType type;

    /**
     * 消息内容
     */
    private T data;

    public static <T> SocketMsg<T> delivery(T data) {
        return new SocketMsg<>(MsgType.DELIVERY, data);
    }

    public static <T> SocketMsg<T> refund(T data) {
        return new SocketMsg<>(MsgType.REFUND, data);
    }
}
