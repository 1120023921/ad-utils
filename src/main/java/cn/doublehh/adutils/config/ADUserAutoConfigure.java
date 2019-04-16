package cn.doublehh.adutils.config;

import cn.doublehh.adutils.service.ADUserService;
import cn.doublehh.adutils.service.DefaultADUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 胡昊
 * Description:
 * Date: 2019/4/15
 * Time: 10:42
 * Create: DoubleH
 */
@Configuration
@ConditionalOnClass(ADUserService.class)
@EnableConfigurationProperties(ADServiceProperties.class)
public class ADUserAutoConfigure {

    private final ADServiceProperties properties;

    @Autowired
    public ADUserAutoConfigure(ADServiceProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "ldap", value = "enabled", havingValue = "true")
    public ADUserService aDUserService() {
        String[] returnedAtts = properties.getReturnedAtts().trim().equals("*") ? null : properties.getReturnedAtts().trim().split(",");
        return new DefaultADUserService(properties.getAdminName(), properties.getAdminPassword(), properties.getLdapURL(), returnedAtts);
    }
}
