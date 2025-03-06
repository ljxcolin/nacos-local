package stu.ljxcolin.nacos.local.ribbon;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PredicateBasedRule;
import com.netflix.loadbalancer.PredicateKey;
import stu.ljxcolin.nacos.local.core.LocalMetadataKeys;
import stu.ljxcolin.nacos.local.core.NacosLocalProperties;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * 过滤本地服务的负载规则
 * @author lijinxuan
 * @since 2025/2/7 16:41
 */
public class NacosLocalRule extends PredicateBasedRule {

    private LocalPredicate localPredicate;
    private final NacosLocalProperties localProperties;

    public NacosLocalRule() {
        this(null);
    }

    public NacosLocalRule(NacosLocalProperties localProperties) {
        this.localProperties = localProperties;
    }

    @Override
    public AbstractServerPredicate getPredicate() {
        return localPredicate;
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        localPredicate = new LocalPredicate(localProperties, this, clientConfig);
    }


    static class LocalPredicate extends AbstractServerPredicate {

        private final NacosLocalProperties localProperties;

        public LocalPredicate(NacosLocalProperties localProperties, IRule rule, IClientConfig clientConfig) {
            super(rule, clientConfig);
            this.localProperties = localProperties;
        }

        @Override
        public boolean apply(@Nullable PredicateKey predicateKey) {
            if (predicateKey == null || !(predicateKey.getServer() instanceof NacosServer)) {
                return true;
            }
            NacosServer server = (NacosServer) predicateKey.getServer();
            String serviceName = getLBStats().getName();
            Map<String, String> metadata = server.getMetadata();
            if (localProperties.isChooseLocal()) {
                if (metadata.containsKey(LocalMetadataKeys.LOCAL) && Boolean.parseBoolean(metadata.get(LocalMetadataKeys.LOCAL))) {
                    List<String> services = localProperties.getServices();
                    String tag = localProperties.getTag();
                    if (services != null && services.contains(serviceName)) {
                        return tag == null || tag.length() == 0 || tag.equals(metadata.get(LocalMetadataKeys.LOCAL_TAG));
                    }
                }
                return false;
            } else {
                return !metadata.containsKey(LocalMetadataKeys.LOCAL) || !Boolean.parseBoolean(metadata.get(LocalMetadataKeys.LOCAL));
            }
        }
    }
}
