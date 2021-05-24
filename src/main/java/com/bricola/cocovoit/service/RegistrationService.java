package com.bricola.cocovoit.service;

import com.bricola.cocovoit.domain.Registration;
import com.bricola.cocovoit.repository.RegistrationRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Registration}.
 */
@Service
@Transactional
public class RegistrationService {

    private final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    private final UserService userService;
    private final RegistrationRepository registrationRepository;

    public RegistrationService(UserService userService, RegistrationRepository registrationRepository) {
        this.userService = userService;
        this.registrationRepository = registrationRepository;
    }

    /**
     * Save a registration.
     *
     * @param registration the entity to save.
     * @return the persisted entity.
     */
    public Registration save(Registration registration) {
        log.debug("Request to save Registration : {}", registration);

        // L'utilisateur associé à la réservation est l'utilisateur courant
        registration.setUser(userService.getUserWithAuthorities().get());

        return registrationRepository.save(registration);
    }

    /**
     * Partially update a registration.
     *
     * @param registration the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Registration> partialUpdate(Registration registration) {
        log.debug("Request to partially update Registration : {}", registration);

        return registrationRepository
            .findById(registration.getId())
            .map(
                existingRegistration -> {
                    return existingRegistration;
                }
            )
            .map(registrationRepository::save);
    }

    /**
     * Get all the registrations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Registration> findAll(Pageable pageable) {
        log.debug("Request to get all Registrations");
        return registrationRepository.findAll(pageable);
    }

    /**
     * Get one registration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Registration> findOne(Long id) {
        log.debug("Request to get Registration : {}", id);
        return registrationRepository.findById(id);
    }

    /**
     * Delete the registration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Registration : {}", id);
        registrationRepository.deleteById(id);
    }
}
