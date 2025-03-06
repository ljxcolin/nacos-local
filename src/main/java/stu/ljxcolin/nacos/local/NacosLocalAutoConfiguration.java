package stu.ljxcolin.nacos.local;

import com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration;
import com.alibaba.cloud.nacos.registry.NacosRegistrationCustomizer;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistryAutoConfiguration;
import com.netflix.loadbalancer.IRule;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import stu.ljxcolin.nacos.local.core.LocalNacosRegistrationCustomizer;
import stu.ljxcolin.nacos.local.core.NacosLocalProperties;
import stu.ljxcolin.nacos.local.ribbon.NacosLocalRule;

/**
 * 本地服务调用自动装配
 *
 * @author lijinxuan
 * @since 2025/2/7 14:32
 */
@Configuration
@ConditionalOnProperty(value = "nacos.local.enabled", havingValue = "true", matchIfMissing = true)
@AutoConfigureAfter({NacosDiscoveryAutoConfiguration.class})
@AutoConfigureBefore({NacosServiceRegistryAutoConfiguration.class, RibbonClientConfiguration.class})
public class NacosLocalAutoConfiguration {

    @Bean
    public NacosLocalProperties nacosLocalProperties() {
        return new NacosLocalProperties();
    }

    @Bean
    @ConditionalOnClass({NacosRegistrationCustomizer.class})
    @ConditionalOnProperty(value = "nacos.local.register-enabled", havingValue = "true")
    public LocalNacosRegistrationCustomizer localNacosRegistrationCustomizer(NacosLocalProperties localProperties) {
        return new LocalNacosRegistrationCustomizer(localProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public IRule nacosLocalRule(NacosLocalProperties localProperties) {
        NacosLocalRule rule = new NacosLocalRule(localProperties);
        rule.initWithNiwsConfig(null);
        return rule;
    }

}
