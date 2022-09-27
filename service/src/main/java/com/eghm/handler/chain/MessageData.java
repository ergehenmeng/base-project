package com.eghm.handler.chain;

import com.eghm.model.User;
import com.eghm.model.dto.ext.UserRegister;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/12/19 17:55
 */
@Data
public class MessageData implements Serializable {

    private static final long serialVersionUID = -4457725372361945283L;

    private User user;

    private UserRegister userRegister;

}
