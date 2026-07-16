package com.eghm.architecture;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapperResourceTest {

    private static final Pattern NAMESPACE = Pattern.compile("<mapper\\s+namespace=\"([^\"]+)\"");

    @Test
    void everyMapperXmlReferencesAnExistingMapperInterface() throws Exception {
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/**/*.xml");

        assertEquals(42, resources.length, "Mapper XML count changed unexpectedly");
        for (Resource resource : resources) {
            String xml = resource.getContentAsString(StandardCharsets.UTF_8);
            Matcher matcher = NAMESPACE.matcher(xml);
            assertTrue(matcher.find(), () -> "Missing mapper namespace: " + resource);
            Class<?> mapperType = Class.forName(matcher.group(1));
            assertTrue(mapperType.isInterface(), () -> "Mapper namespace is not an interface: " + mapperType);
        }
    }
}
