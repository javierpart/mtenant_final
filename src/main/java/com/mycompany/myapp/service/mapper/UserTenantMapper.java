package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.UserTenant;
import com.mycompany.myapp.service.dto.UserTenantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserTenant} and its DTO {@link UserTenantDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface UserTenantMapper extends EntityMapper<UserTenantDTO, UserTenant> {
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    UserTenantDTO toDto(UserTenant s);
}
