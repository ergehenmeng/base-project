package com.eghm.platform.config.service;

import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.core.constants.ConfigConstant;
import com.eghm.foundation.core.service.ResourcePathResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultResourcePathResolver implements ResourcePathResolver {

    private final SysConfigApi sysConfigApi;

    private final ApplicationProperties applicationProperties;

    @Override
    public String resolve(String path) {
        String fileAddress = sysConfigApi.getString(ConfigConstant.FILE_SERVER_HOST);
        if (path.startsWith(fileAddress)) {
            return "file://" + applicationProperties.getStorage().getLocal().getAbsolutePath() + path.replace(fileAddress, "");
        }
        return path;
    }
}
