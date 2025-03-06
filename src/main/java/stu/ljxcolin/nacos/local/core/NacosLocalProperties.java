package stu.ljxcolin.nacos.local.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 本地服务配置
 *
 * @author lijinxuan
 * @since 2025/2/7 14:28
 */
@ConfigurationProperties("nacos.local")
public class NacosLocalProperties {

    /**
     * 是否启用本地服务调用功能
     */
    private boolean enabled = true;
    /**
     * 是否注册为本地服务
     */
    private boolean registerEnabled = false;
    /**
     * 是否选择本地服务进行服务调用
     */
    private boolean chooseLocal = false;
    /**
     * 开启本地服务调用的服务集合
     */
    private List<String> services;
    /**
     * 本地服务标签
     */
    private String tag;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isRegisterEnabled() {
        return registerEnabled;
    }

    public void setRegisterEnabled(boolean registerEnabled) {
        this.registerEnabled = registerEnabled;
    }

    public boolean isChooseLocal() {
        return chooseLocal;
    }

    public void setChooseLocal(boolean chooseLocal) {
        this.chooseLocal = chooseLocal;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
