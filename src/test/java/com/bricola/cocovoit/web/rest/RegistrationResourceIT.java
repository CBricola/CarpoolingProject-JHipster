package com.bricola.cocovoit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bricola.cocovoit.IntegrationTest;
import com.bricola.cocovoit.domain.Registration;
import com.bricola.cocovoit.repository.RegistrationRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RegistrationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RegistrationResourceIT {

    private static final String ENTITY_API_URL = "/api/registrations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegistrationMockMvc;

    private Registration registration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Registration createEntity(EntityManager em) {
        Registration registration = new Registration();
        return registration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Registration createUpdatedEntity(EntityManager em) {
        Registration registration = new Registration();
        return registration;
    }

    @BeforeEach
    public void initTest() {
        registration = createEntity(em);
    }

    @Test
    @Transactional
    void createRegistration() throws Exception {
        int databaseSizeBeforeCreate = registrationRepository.findAll().size();
        // Create the Registration
        restRegistrationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registration))
            )
            .andExpect(status().isCreated());

        // Validate the Registration in the database
        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeCreate + 1);
        Registration testRegistration = registrationList.get(registrationList.size() - 1);
    }

    @Test
    @Transactional
    void createRegistrationWithExistingId() throws Exception {
        // Create the Registration with an existing ID
        registration.setId(1L);

        int databaseSizeBeforeCreate = registrationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistrationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registration))
            )
            .andExpect(status().isBadRequest());

        // Validate the Registration in the database
        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRegistrations() throws Exception {
        // Initialize the database
        registrationRepository.saveAndFlush(registration);

        // Get all the registrationList
        restRegistrationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registration.getId().intValue())));
    }

    @Test
    @Transactional
    void getRegistration() throws Exception {
        // Initialize the database
        registrationRepository.saveAndFlush(registration);

        // Get the registration
        restRegistrationMockMvc
            .perform(get(ENTITY_API_URL_ID, registration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(registration.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingRegistration() throws Exception {
        // Get the registration
        restRegistrationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRegistration() throws Exception {
        // Initialize the database
        registrationRepository.saveAndFlush(registration);

        int databaseSizeBeforeUpdate = registrationRepository.findAll().size();

        // Update the registration
        Registration updatedRegistration = registrationRepository.findById(registration.getId()).get();
        // Disconnect from session so that the updates on updatedRegistration are not directly saved in db
        em.detach(updatedRegistration);

        restRegistrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRegistration.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRegistration))
            )
            .andExpect(status().isOk());

        // Validate the Registration in the database
        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeUpdate);
        Registration testRegistration = registrationList.get(registrationList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingRegistration() throws Exception {
        int databaseSizeBeforeUpdate = registrationRepository.findAll().size();
        registration.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, registration.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registration))
            )
            .andExpect(status().isBadRequest());

        // Validate the Registration in the database
        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegistration() throws Exception {
        int databaseSizeBeforeUpdate = registrationRepository.findAll().size();
        registration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registration))
            )
            .andExpect(status().isBadRequest());

        // Validate the Registration in the database
        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegistration() throws Exception {
        int databaseSizeBeforeUpdate = registrationRepository.findAll().size();
        registration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistrationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registration))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Registration in the database
        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegistrationWithPatch() throws Exception {
        // Initialize the database
        registrationRepository.saveAndFlush(registration);

        int databaseSizeBeforeUpdate = registrationRepository.findAll().size();

        // Update the registration using partial update
        Registration partialUpdatedRegistration = new Registration();
        partialUpdatedRegistration.setId(registration.getId());

        restRegistrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegistration.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistration))
            )
            .andExpect(status().isOk());

        // Validate the Registration in the database
        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeUpdate);
        Registration testRegistration = registrationList.get(registrationList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateRegistrationWithPatch() throws Exception {
        // Initialize the database
        registrationRepository.saveAndFlush(registration);

        int databaseSizeBeforeUpdate = registrationRepository.findAll().size();

        // Update the registration using partial update
        Registration partialUpdatedRegistration = new Registration();
        partialUpdatedRegistration.setId(registration.getId());

        restRegistrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegistration.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistration))
            )
            .andExpect(status().isOk());

        // Validate the Registration in the database
        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeUpdate);
        Registration testRegistration = registrationList.get(registrationList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingRegistration() throws Exception {
        int databaseSizeBeforeUpdate = registrationRepository.findAll().size();
        registration.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, registration.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registration))
            )
            .andExpect(status().isBadRequest());

        // Validate the Registration in the database
        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegistration() throws Exception {
        int databaseSizeBeforeUpdate = registrationRepository.findAll().size();
        registration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registration))
            )
            .andExpect(status().isBadRequest());

        // Validate the Registration in the database
        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegistration() throws Exception {
        int databaseSizeBeforeUpdate = registrationRepository.findAll().size();
        registration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistrationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registration))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Registration in the database
        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegistration() throws Exception {
        // Initialize the database
        registrationRepository.saveAndFlush(registration);

        int databaseSizeBeforeDelete = registrationRepository.findAll().size();

        // Delete the registration
        restRegistrationMockMvc
            .perform(delete(ENTITY_API_URL_ID, registration.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
