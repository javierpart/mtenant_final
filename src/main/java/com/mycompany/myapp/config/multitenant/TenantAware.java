package com.mycompany.myapp.config.multitenant;

public interface TenantAware {
    String getTenantId();

    void setTenantId(String tenantId);
}
