package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.UserTenant;
import com.mycompany.myapp.repository.UserTenantRepository;
import com.mycompany.myapp.service.UserTenantService;
import com.mycompany.myapp.service.dto.UserTenantDTO;
import com.mycompany.myapp.service.mapper.UserTenantMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserTenant}.
 */
@Service
@Transactional
public class UserTenantServiceImpl implements UserTenantService {

    private final Logger log = LoggerFactory.getLogger(UserTenantServiceImpl.class);

    private final UserTenantRepository userTenantRepository;

    private final UserTenantMapper userTenantMapper;

    public UserTenantServiceImpl(UserTenantRepository userTenantRepository, UserTenantMapper userTenantMapper) {
        this.userTenantRepository = userTenantRepository;
        this.userTenantMapper = userTenantMapper;
    }

    @Override
    public UserTenantDTO save(UserTenantDTO userTenantDTO) {
        log.debug("Request to save UserTenant : {}", userTenantDTO);
        UserTenant userTenant = userTenantMapper.toEntity(userTenantDTO);
        userTenant = userTenantRepository.save(userTenant);
        return userTenantMapper.toDto(userTenant);
    }

    @Override
    public Optional<UserTenantDTO> partialUpdate(UserTenantDTO userTenantDTO) {
        log.debug("Request to partially update UserTenant : {}", userTenantDTO);

        return userTenantRepository
            .findById(userTenantDTO.getId())
            .map(existingUserTenant -> {
                userTenantMapper.partialUpdate(existingUserTenant, userTenantDTO);

                return existingUserTenant;
            })
            .map(userTenantRepository::save)
            .map(userTenantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserTenantDTO> findAll() {
        log.debug("Request to get all UserTenants");
        return userTenantRepository.findAll().stream().map(userTenantMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserTenantDTO> findOne(Long id) {
        log.debug("Request to get UserTenant : {}", id);
        return userTenantRepository.findById(id).map(userTenantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserTenant : {}", id);
        userTenantRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public String getTenantIdByUser(Optional<User> user) {
        String tenant;
        if (!user.isPresent()) {
            tenant = "0";
        } else {
            UserTenant userT = userTenantRepository.findByUser(user);
            tenant = userT.getTenantId();
        }

        return tenant;
    }
}
