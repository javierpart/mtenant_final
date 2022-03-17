package com.mycompany.myapp.config.multitenant.Entity;

import com.mycompany.myapp.config.multitenant.TenantAware;
import com.mycompany.myapp.config.multitenant.TenantListener;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
@EntityListeners(TenantListener.class)
public abstract class BaseEntityId implements TenantAware, Serializable {

    @Column(name = "tenant_id")
    private String tenantId;

    @Id
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return this.id;
    }

    public BaseEntityId id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    @Override
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public BaseEntityId() {}

    public BaseEntityId(String tenantId, Long id) {
        this.tenantId = tenantId;
        this.id = id;
    }

    public BaseEntityId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("Should be implemented by subclass.");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Should be implemented by subclass.");
    }

    @PrePersist
    private void onPrePersist() {
        id = generateIdLong();
        // id = 1298736L;
    }

    @PreUpdate
    private void onPreUpdate() {}

    public Long generateIdLong() {
        long leftLimit = 1L;
        long rightLimit = 10000000L;
        long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        return generatedLong;
    }
}
