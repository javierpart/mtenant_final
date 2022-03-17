package com.mycompany.myapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "global.datasource.truststore")
public class DataSourceTruststoreProperties {

    private String location;
    private String password;
    private String content;
    private String type;

    public DataSourceTruststoreProperties() {}

    public DataSourceTruststoreProperties(String location, String password, String content, String type) {
        this.location = location;
        this.password = password;
        this.content = content;
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
