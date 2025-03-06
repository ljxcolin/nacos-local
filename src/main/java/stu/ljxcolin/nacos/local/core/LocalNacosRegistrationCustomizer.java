package stu.ljxcolin.nacos.local.core;

import com.alibaba.cloud.nacos.registry.NacosRegistration;
import com.alibaba.cloud.nacos.registry.NacosRegistrationCustomizer;

import java.util.Map;

/**
 * 服务注册自定义，元数据标记为本地服务
 *
 * @author lijinxuan
 * @since 2025/2/7 16:47
 */
public class LocalNacosRegistrationCustomizer implements NacosRegistrationCustomizer {

    private final NacosLocalProperties localProperties;

    public LocalNacosRegistrationCustomizer(NacosLocalProperties localProperties) {
        this.localProperties = localProperties;
    }

    @Override
    public void customize(NacosRegistration registration) {
        if (localProperties.isRegisterEnabled()) {
            Map<String, String> metadata = registration.getNacosDiscoveryProperties().getMetadata();
            metadata.put(LocalMetadataKeys.LOCAL, String.valueOf(localProperties.isRegisterEnabled()));
            metadata.put(LocalMetadataKeys.LOCAL_TAG, localProperties.getTag());
        }
    }
}
