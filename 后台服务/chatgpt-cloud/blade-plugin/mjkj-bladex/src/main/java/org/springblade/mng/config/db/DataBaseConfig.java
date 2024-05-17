package org.springblade.mng.config.db;

import org.springblade.core.tool.utils.Func;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("dataBaseConfig")
@ConfigurationProperties(
        prefix = "spring.datasource"
)
public class DataBaseConfig {
    private String url;
    private String username;
    private String password;
    private String driverClassName;

    public DataBaseConfig() {
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return this.driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
    	if(Func.isEmpty(driverClassName)){
    		driverClassName="com.mysql.cj.jdbc.Driver";
		}
        this.driverClassName = driverClassName;
    }
}
