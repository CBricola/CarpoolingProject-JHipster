package com.bricola.cocovoit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bricola.cocovoit.IntegrationTest;
import com.bricola.cocovoit.domain.Path;
import com.bricola.cocovoit.repository.PathRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link PathResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PathResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_NUMBER_OF_PASSENGERS = 1;
    private static final Integer UPDATED_NUMBER_OF_PASSENGERS = 2;

    private static final String DEFAULT_DEPARTURE_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTURE_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_ARRIVAL_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_ARRIVAL_PLACE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/paths";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PathRepository pathRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPathMockMvc;

    private Path path;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Path createEntity(EntityManager em) {
        Path path = new Path()
            .date(DEFAULT_DATE)
            .numberOfPassengers(DEFAULT_NUMBER_OF_PASSENGERS)
            .departurePlace(DEFAULT_DEPARTURE_PLACE)
            .arrivalPlace(DEFAULT_ARRIVAL_PLACE);
        return path;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Path createUpdatedEntity(EntityManager em) {
        Path path = new Path()
            .date(UPDATED_DATE)
            .numberOfPassengers(UPDATED_NUMBER_OF_PASSENGERS)
            .departurePlace(UPDATED_DEPARTURE_PLACE)
            .arrivalPlace(UPDATED_ARRIVAL_PLACE);
        return path;
    }

    @BeforeEach
    public void initTest() {
        path = createEntity(em);
    }

    @Test
    @Transactional
    void createPath() throws Exception {
        int databaseSizeBeforeCreate = pathRepository.findAll().size();
        // Create the Path
        restPathMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(path))
            )
            .andExpect(status().isCreated());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeCreate + 1);
        Path testPath = pathList.get(pathList.size() - 1);
        assertThat(testPath.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPath.getNumberOfPassengers()).isEqualTo(DEFAULT_NUMBER_OF_PASSENGERS);
        assertThat(testPath.getDeparturePlace()).isEqualTo(DEFAULT_DEPARTURE_PLACE);
        assertThat(testPath.getArrivalPlace()).isEqualTo(DEFAULT_ARRIVAL_PLACE);
    }

    @Test
    @Transactional
    void createPathWithExistingId() throws Exception {
        // Create the Path with an existing ID
        path.setId(1L);

        int databaseSizeBeforeCreate = pathRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPathMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(path))
            )
            .andExpect(status().isBadRequest());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pathRepository.findAll().size();
        // set the field null
        path.setDate(null);

        // Create the Path, which fails.

        restPathMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(path))
            )
            .andExpect(status().isBadRequest());

        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumberOfPassengersIsRequired() throws Exception {
        int databaseSizeBeforeTest = pathRepository.findAll().size();
        // set the field null
        path.setNumberOfPassengers(null);

        // Create the Path, which fails.

        restPathMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(path))
            )
            .andExpect(status().isBadRequest());

        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeparturePlaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = pathRepository.findAll().size();
        // set the field null
        path.setDeparturePlace(null);

        // Create the Path, which fails.

        restPathMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(path))
            )
            .andExpect(status().isBadRequest());

        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkArrivalPlaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = pathRepository.findAll().size();
        // set the field null
        path.setArrivalPlace(null);

        // Create the Path, which fails.

        restPathMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(path))
            )
            .andExpect(status().isBadRequest());

        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaths() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);

        // Get all the pathList
        restPathMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(path.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].numberOfPassengers").value(hasItem(DEFAULT_NUMBER_OF_PASSENGERS)))
            .andExpect(jsonPath("$.[*].departurePlace").value(hasItem(DEFAULT_DEPARTURE_PLACE)))
            .andExpect(jsonPath("$.[*].arrivalPlace").value(hasItem(DEFAULT_ARRIVAL_PLACE)));
    }

    @Test
    @Transactional
    void getPath() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);

        // Get the path
        restPathMockMvc
            .perform(get(ENTITY_API_URL_ID, path.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(path.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.numberOfPassengers").value(DEFAULT_NUMBER_OF_PASSENGERS))
            .andExpect(jsonPath("$.departurePlace").value(DEFAULT_DEPARTURE_PLACE))
            .andExpect(jsonPath("$.arrivalPlace").value(DEFAULT_ARRIVAL_PLACE));
    }

    @Test
    @Transactional
    void getNonExistingPath() throws Exception {
        // Get the path
        restPathMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPath() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);

        int databaseSizeBeforeUpdate = pathRepository.findAll().size();

        // Update the path
        Path updatedPath = pathRepository.findById(path.getId()).get();
        // Disconnect from session so that the updates on updatedPath are not directly saved in db
        em.detach(updatedPath);
        updatedPath
            .date(UPDATED_DATE)
            .numberOfPassengers(UPDATED_NUMBER_OF_PASSENGERS)
            .departurePlace(UPDATED_DEPARTURE_PLACE)
            .arrivalPlace(UPDATED_ARRIVAL_PLACE);

        restPathMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPath.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPath))
            )
            .andExpect(status().isOk());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
        Path testPath = pathList.get(pathList.size() - 1);
        assertThat(testPath.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPath.getNumberOfPassengers()).isEqualTo(UPDATED_NUMBER_OF_PASSENGERS);
        assertThat(testPath.getDeparturePlace()).isEqualTo(UPDATED_DEPARTURE_PLACE);
        assertThat(testPath.getArrivalPlace()).isEqualTo(UPDATED_ARRIVAL_PLACE);
    }

    @Test
    @Transactional
    void putNonExistingPath() throws Exception {
        int databaseSizeBeforeUpdate = pathRepository.findAll().size();
        path.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPathMockMvc
            .perform(
                put(ENTITY_API_URL_ID, path.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(path))
            )
            .andExpect(status().isBadRequest());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPath() throws Exception {
        int databaseSizeBeforeUpdate = pathRepository.findAll().size();
        path.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPathMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(path))
            )
            .andExpect(status().isBadRequest());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPath() throws Exception {
        int databaseSizeBeforeUpdate = pathRepository.findAll().size();
        path.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPathMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(path))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePathWithPatch() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);

        int databaseSizeBeforeUpdate = pathRepository.findAll().size();

        // Update the path using partial update
        Path partialUpdatedPath = new Path();
        partialUpdatedPath.setId(path.getId());

        partialUpdatedPath.numberOfPassengers(UPDATED_NUMBER_OF_PASSENGERS).departurePlace(UPDATED_DEPARTURE_PLACE);

        restPathMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPath.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPath))
            )
            .andExpect(status().isOk());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
        Path testPath = pathList.get(pathList.size() - 1);
        assertThat(testPath.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPath.getNumberOfPassengers()).isEqualTo(UPDATED_NUMBER_OF_PASSENGERS);
        assertThat(testPath.getDeparturePlace()).isEqualTo(UPDATED_DEPARTURE_PLACE);
        assertThat(testPath.getArrivalPlace()).isEqualTo(DEFAULT_ARRIVAL_PLACE);
    }

    @Test
    @Transactional
    void fullUpdatePathWithPatch() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);

        int databaseSizeBeforeUpdate = pathRepository.findAll().size();

        // Update the path using partial update
        Path partialUpdatedPath = new Path();
        partialUpdatedPath.setId(path.getId());

        partialUpdatedPath
            .date(UPDATED_DATE)
            .numberOfPassengers(UPDATED_NUMBER_OF_PASSENGERS)
            .departurePlace(UPDATED_DEPARTURE_PLACE)
            .arrivalPlace(UPDATED_ARRIVAL_PLACE);

        restPathMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPath.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPath))
            )
            .andExpect(status().isOk());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
        Path testPath = pathList.get(pathList.size() - 1);
        assertThat(testPath.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPath.getNumberOfPassengers()).isEqualTo(UPDATED_NUMBER_OF_PASSENGERS);
        assertThat(testPath.getDeparturePlace()).isEqualTo(UPDATED_DEPARTURE_PLACE);
        assertThat(testPath.getArrivalPlace()).isEqualTo(UPDATED_ARRIVAL_PLACE);
    }

    @Test
    @Transactional
    void patchNonExistingPath() throws Exception {
        int databaseSizeBeforeUpdate = pathRepository.findAll().size();
        path.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPathMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, path.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(path))
            )
            .andExpect(status().isBadRequest());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPath() throws Exception {
        int databaseSizeBeforeUpdate = pathRepository.findAll().size();
        path.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPathMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(path))
            )
            .andExpect(status().isBadRequest());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPath() throws Exception {
        int databaseSizeBeforeUpdate = pathRepository.findAll().size();
        path.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPathMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(path))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePath() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);

        int databaseSizeBeforeDelete = pathRepository.findAll().size();

        // Delete the path
        restPathMockMvc
            .perform(delete(ENTITY_API_URL_ID, path.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
