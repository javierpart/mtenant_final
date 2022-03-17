package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.UserTenant} entity.
 */
public class UserTenantDTO implements Serializable {

    private Long id;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserTenantDTO)) {
            return false;
        }

        UserTenantDTO userTenantDTO = (UserTenantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userTenantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserTenantDTO{" +
            "id=" + getId() +
            ", user=" + getUser() +
            "}";
    }
}
