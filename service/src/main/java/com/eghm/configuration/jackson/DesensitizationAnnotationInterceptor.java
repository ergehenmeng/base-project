package com.eghm.configuration.jackson;

import com.eghm.annotation.Desensitization;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;

/**
 * @author 二哥很猛
 * @since 2023/12/27
 */
public class DesensitizationAnnotationInterceptor extends NopAnnotationIntrospector {

    @Override
    public Object findSerializer(Annotated am) {
        Desensitization annotation = am.getAnnotation(Desensitization.class);
        if (annotation != null) {
            return new DesensitizationSerializer(annotation.value());
        }
        return super.findSerializer(am);
    }
}
