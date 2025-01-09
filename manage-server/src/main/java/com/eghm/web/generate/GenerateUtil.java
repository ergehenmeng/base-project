package com.eghm.web.generate;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReflectUtil;
import com.eghm.configuration.template.FreemarkerTemplate;
import com.eghm.dto.ext.GenerateFile;
import com.eghm.dto.ext.GenerateService;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 二哥很猛
 * @since 2024/4/2
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenerateUtil {

    private static final String ROOT_PACKAGE = "\\src\\main\\java\\";

    private static String generate(Class<?> cls, String packageName, String suffix) throws URISyntaxException {
        URL url = GenerateUtil.class.getResource("/");
        assert url != null;
        String path = new File(url.toURI()).getParentFile().getParentFile().getParentFile().getAbsolutePath();
        return path + "\\service" + ROOT_PACKAGE + packageName.replace(".", "\\") + "\\" + cls.getSimpleName() + suffix + ".java";
    }

    public static void generateRequest(Class<?> cls, FreemarkerTemplate freemarkerTemplate) throws URISyntaxException, IOException {
        GenerateUtil.generateRequest(cls, "com.eghm.dto.business","com.eghm.service.business", freemarkerTemplate);
    }

    public static void generateRequest(Class<?> cls, String requestPackage, String implPackage, FreemarkerTemplate freemarkerTemplate) throws URISyntaxException, IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("template", GenerateUtil.generateRequest(cls, requestPackage));
        URL url = GenerateUtil.class.getResource("/");
        assert url != null;
        String parentPath = new File(url.toURI()).getParentFile().getParentFile().getAbsolutePath() + ROOT_PACKAGE + "com\\eghm\\web\\generate\\template\\";
        String addFile = FileUtil.readString(parentPath + "AddRequest.ftl", StandardCharsets.UTF_8);
        String editFile = FileUtil.readString(parentPath + "EditRequest.ftl", StandardCharsets.UTF_8);
        String queryFile = FileUtil.readString(parentPath + "QueryRequest.ftl", StandardCharsets.UTF_8);
        String addFileStr = freemarkerTemplate.render(addFile, map);
        String editFileStr = freemarkerTemplate.render(editFile, map);
        String queryFileStr = freemarkerTemplate.render(queryFile, map);
        String addPath = generate(cls, requestPackage, "AddRequest");
        String editPath = generate(cls, requestPackage, "EditRequest");
        String queryPath = generate(cls, requestPackage, "QueryRequest");
        gen(addFileStr, editFileStr, queryFileStr, addPath, editPath, queryPath);

        generateService(cls, implPackage, requestPackage, freemarkerTemplate);
    }

    private static void gen(String addFileStr, String editFileStr, String queryFileStr, String addPath, String editPath, String queryPath) throws IOException {
        BufferedWriter writer = FileUtil.getWriter(addPath, StandardCharsets.UTF_8, false);
        writer.write(addFileStr);
        writer.flush();
        writer = FileUtil.getWriter(editPath, StandardCharsets.UTF_8, false);
        writer.write(editFileStr);
        writer.flush();
        writer = FileUtil.getWriter(queryPath, StandardCharsets.UTF_8, false);
        writer.write(queryFileStr);
        writer.flush();
        writer.close();
    }

    private static void generateService(Class<?> cls, String implPackage, String requestPackage, FreemarkerTemplate freemarkerTemplate) throws URISyntaxException, IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("template", GenerateUtil.generateService(cls, implPackage, requestPackage));
        URL url = GenerateUtil.class.getResource("/");
        assert url != null;
        String parentPath = new File(url.toURI()).getParentFile().getParentFile().getAbsolutePath() + ROOT_PACKAGE + "com\\eghm\\web\\generate\\template\\";
        String service = FileUtil.readString(parentPath + "Service.ftl", StandardCharsets.UTF_8);
        String serviceImpl = FileUtil.readString(parentPath + "ServiceImpl.ftl", StandardCharsets.UTF_8);
        String controller = FileUtil.readString(parentPath + "Controller.ftl", StandardCharsets.UTF_8);
        String serviceStr = freemarkerTemplate.render(service, map);
        String serviceImplStr = freemarkerTemplate.render(serviceImpl, map);
        String controllerStr = freemarkerTemplate.render(controller, map);
        String servicePath = generate(cls, implPackage, "Service");
        String controllerPath = new File(url.toURI()).getParentFile().getParentFile().getAbsolutePath() + ROOT_PACKAGE + "com\\eghm\\web\\controller\\" + cls.getSimpleName() + "Controller.java";
        String serviceImplPath = generate(cls, implPackage + ".impl", "ServiceImpl");
        gen(serviceStr, serviceImplStr, controllerStr, servicePath, serviceImplPath, controllerPath);
    }

    private static GenerateFile generateRequest(Class<?> cls, String packageName) {
        Field[] fields = ReflectUtil.getFieldsDirectly(cls, false);
        GenerateFile template = new GenerateFile();
        template.setPackageName(packageName);
        template.setFileName(cls.getSimpleName());
        List<GenerateFile.FieldDesc> descList = new ArrayList<>();
        for (Field field : fields) {
            ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
            if ( annotation == null) {
                continue;
            }
            GenerateFile.FieldDesc desc = new GenerateFile.FieldDesc();
            desc.setFieldName(field.getName());
            desc.setFieldType(field.getType().getSimpleName());
            String value = annotation.value();
            if (value != null) {
                desc.setDesc(value.split(" ")[0]);
            } else {
                desc.setDesc("");
            }
            descList.add(desc);
        }
        template.setFieldList(descList);
        return template;
    }

    private static GenerateService generateService(Class<?> cls, String implPackage, String requestPackage) {
        GenerateService service = new GenerateService();
        service.setImplPackage(implPackage);
        service.setFileFullName(cls.getName());
        service.setFileName(cls.getSimpleName());
        service.setRequestPackage(requestPackage);
        service.setSelectRequest(cls.getSimpleName() + "QueryRequest");
        service.setCreateRequest(cls.getSimpleName() + "AddRequest");
        service.setUpdateRequest(cls.getSimpleName() + "EditRequest");
        service.setResponse(cls.getSimpleName() + "Response");
        return service;
    }
}
