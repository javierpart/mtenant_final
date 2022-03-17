package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.UserTenant;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserTenant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserTenantRepository extends JpaRepository<UserTenant, Long> {
    UserTenant findByUser(Optional<User> user);
}
