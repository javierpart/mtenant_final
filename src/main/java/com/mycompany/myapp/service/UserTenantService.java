package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.UserTenantDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.UserTenant}.
 */
public interface UserTenantService {
    /**
     * Save a userTenant.
     *
     * @param userTenantDTO the entity to save.
     * @return the persisted entity.
     */
    UserTenantDTO save(UserTenantDTO userTenantDTO);

    /**
     * Partially updates a userTenant.
     *
     * @param userTenantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserTenantDTO> partialUpdate(UserTenantDTO userTenantDTO);

    /**
     * Get all the userTenants.
     *
     * @return the list of entities.
     */
    List<UserTenantDTO> findAll();

    /**
     * Get the "id" userTenant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserTenantDTO> findOne(Long id);

    /**
     * Delete the "id" userTenant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
