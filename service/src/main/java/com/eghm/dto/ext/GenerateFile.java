package com.eghm.dto.ext;

import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/4/2
 */

@Data
public class GenerateFile {

    private String packageName;

    private List<FieldDesc> fieldList;

    private String fileName;

    @Data
    public static class FieldDesc {

        private String fieldName;

        private String fieldType;

        private String desc;
    }

}
