package com.tennis.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tennis.app.IntegrationTest;
import com.tennis.app.domain.AvgStat;
import com.tennis.app.repository.AvgStatRepository;
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
 * Integration tests for the {@link AvgStatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AvgStatResourceIT {

    private static final Long DEFAULT_AVG_ACES = 1L;
    private static final Long UPDATED_AVG_ACES = 2L;

    private static final Long DEFAULT_AVG_DOUBLE_FAULTS = 1L;
    private static final Long UPDATED_AVG_DOUBLE_FAULTS = 2L;

    private static final Long DEFAULT_AVG_SERVICE_POINTS = 1L;
    private static final Long UPDATED_AVG_SERVICE_POINTS = 2L;

    private static final Long DEFAULT_AVG_FIRST_SERVE_IN = 1L;
    private static final Long UPDATED_AVG_FIRST_SERVE_IN = 2L;

    private static final Long DEFAULT_AVG_FIRST_SERVE_WON = 1L;
    private static final Long UPDATED_AVG_FIRST_SERVE_WON = 2L;

    private static final Long DEFAULT_AVG_SECOND_SERVE_WON = 1L;
    private static final Long UPDATED_AVG_SECOND_SERVE_WON = 2L;

    private static final Long DEFAULT_AVG_SERVICE_GAMES = 1L;
    private static final Long UPDATED_AVG_SERVICE_GAMES = 2L;

    private static final Long DEFAULT_AVG_SAVED_BREAK_POINTS = 1L;
    private static final Long UPDATED_AVG_SAVED_BREAK_POINTS = 2L;

    private static final Long DEFAULT_AVG_FACED_BREAK_POINTS = 1L;
    private static final Long UPDATED_AVG_FACED_BREAK_POINTS = 2L;

    private static final String ENTITY_API_URL = "/api/avg-stats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AvgStatRepository avgStatRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvgStatMockMvc;

    private AvgStat avgStat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvgStat createEntity(EntityManager em) {
        AvgStat avgStat = new AvgStat()
            .avgAces(DEFAULT_AVG_ACES)
            .avgDoubleFaults(DEFAULT_AVG_DOUBLE_FAULTS)
            .avgServicePoints(DEFAULT_AVG_SERVICE_POINTS)
            .avgFirstServeIn(DEFAULT_AVG_FIRST_SERVE_IN)
            .avgFirstServeWon(DEFAULT_AVG_FIRST_SERVE_WON)
            .avgSecondServeWon(DEFAULT_AVG_SECOND_SERVE_WON)
            .avgServiceGames(DEFAULT_AVG_SERVICE_GAMES)
            .avgSavedBreakPoints(DEFAULT_AVG_SAVED_BREAK_POINTS)
            .avgFacedBreakPoints(DEFAULT_AVG_FACED_BREAK_POINTS);
        return avgStat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvgStat createUpdatedEntity(EntityManager em) {
        AvgStat avgStat = new AvgStat()
            .avgAces(UPDATED_AVG_ACES)
            .avgDoubleFaults(UPDATED_AVG_DOUBLE_FAULTS)
            .avgServicePoints(UPDATED_AVG_SERVICE_POINTS)
            .avgFirstServeIn(UPDATED_AVG_FIRST_SERVE_IN)
            .avgFirstServeWon(UPDATED_AVG_FIRST_SERVE_WON)
            .avgSecondServeWon(UPDATED_AVG_SECOND_SERVE_WON)
            .avgServiceGames(UPDATED_AVG_SERVICE_GAMES)
            .avgSavedBreakPoints(UPDATED_AVG_SAVED_BREAK_POINTS)
            .avgFacedBreakPoints(UPDATED_AVG_FACED_BREAK_POINTS);
        return avgStat;
    }

    @BeforeEach
    public void initTest() {
        avgStat = createEntity(em);
    }

    @Test
    @Transactional
    void createAvgStat() throws Exception {
        int databaseSizeBeforeCreate = avgStatRepository.findAll().size();
        // Create the AvgStat
        restAvgStatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avgStat))
            )
            .andExpect(status().isCreated());

        // Validate the AvgStat in the database
        List<AvgStat> avgStatList = avgStatRepository.findAll();
        assertThat(avgStatList).hasSize(databaseSizeBeforeCreate + 1);
        AvgStat testAvgStat = avgStatList.get(avgStatList.size() - 1);
        assertThat(testAvgStat.getAvgAces()).isEqualTo(DEFAULT_AVG_ACES);
        assertThat(testAvgStat.getAvgDoubleFaults()).isEqualTo(DEFAULT_AVG_DOUBLE_FAULTS);
        assertThat(testAvgStat.getAvgServicePoints()).isEqualTo(DEFAULT_AVG_SERVICE_POINTS);
        assertThat(testAvgStat.getAvgFirstServeIn()).isEqualTo(DEFAULT_AVG_FIRST_SERVE_IN);
        assertThat(testAvgStat.getAvgFirstServeWon()).isEqualTo(DEFAULT_AVG_FIRST_SERVE_WON);
        assertThat(testAvgStat.getAvgSecondServeWon()).isEqualTo(DEFAULT_AVG_SECOND_SERVE_WON);
        assertThat(testAvgStat.getAvgServiceGames()).isEqualTo(DEFAULT_AVG_SERVICE_GAMES);
        assertThat(testAvgStat.getAvgSavedBreakPoints()).isEqualTo(DEFAULT_AVG_SAVED_BREAK_POINTS);
        assertThat(testAvgStat.getAvgFacedBreakPoints()).isEqualTo(DEFAULT_AVG_FACED_BREAK_POINTS);
    }

    @Test
    @Transactional
    void createAvgStatWithExistingId() throws Exception {
        // Create the AvgStat with an existing ID
        avgStat.setId(1L);

        int databaseSizeBeforeCreate = avgStatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvgStatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avgStat))
            )
            .andExpect(status().isBadRequest());

        // Validate the AvgStat in the database
        List<AvgStat> avgStatList = avgStatRepository.findAll();
        assertThat(avgStatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAvgStats() throws Exception {
        // Initialize the database
        avgStatRepository.saveAndFlush(avgStat);

        // Get all the avgStatList
        restAvgStatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avgStat.getId().intValue())))
            .andExpect(jsonPath("$.[*].avgAces").value(hasItem(DEFAULT_AVG_ACES.intValue())))
            .andExpect(jsonPath("$.[*].avgDoubleFaults").value(hasItem(DEFAULT_AVG_DOUBLE_FAULTS.intValue())))
            .andExpect(jsonPath("$.[*].avgServicePoints").value(hasItem(DEFAULT_AVG_SERVICE_POINTS.intValue())))
            .andExpect(jsonPath("$.[*].avgFirstServeIn").value(hasItem(DEFAULT_AVG_FIRST_SERVE_IN.intValue())))
            .andExpect(jsonPath("$.[*].avgFirstServeWon").value(hasItem(DEFAULT_AVG_FIRST_SERVE_WON.intValue())))
            .andExpect(jsonPath("$.[*].avgSecondServeWon").value(hasItem(DEFAULT_AVG_SECOND_SERVE_WON.intValue())))
            .andExpect(jsonPath("$.[*].avgServiceGames").value(hasItem(DEFAULT_AVG_SERVICE_GAMES.intValue())))
            .andExpect(jsonPath("$.[*].avgSavedBreakPoints").value(hasItem(DEFAULT_AVG_SAVED_BREAK_POINTS.intValue())))
            .andExpect(jsonPath("$.[*].avgFacedBreakPoints").value(hasItem(DEFAULT_AVG_FACED_BREAK_POINTS.intValue())));
    }

    @Test
    @Transactional
    void getAvgStat() throws Exception {
        // Initialize the database
        avgStatRepository.saveAndFlush(avgStat);

        // Get the avgStat
        restAvgStatMockMvc
            .perform(get(ENTITY_API_URL_ID, avgStat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avgStat.getId().intValue()))
            .andExpect(jsonPath("$.avgAces").value(DEFAULT_AVG_ACES.intValue()))
            .andExpect(jsonPath("$.avgDoubleFaults").value(DEFAULT_AVG_DOUBLE_FAULTS.intValue()))
            .andExpect(jsonPath("$.avgServicePoints").value(DEFAULT_AVG_SERVICE_POINTS.intValue()))
            .andExpect(jsonPath("$.avgFirstServeIn").value(DEFAULT_AVG_FIRST_SERVE_IN.intValue()))
            .andExpect(jsonPath("$.avgFirstServeWon").value(DEFAULT_AVG_FIRST_SERVE_WON.intValue()))
            .andExpect(jsonPath("$.avgSecondServeWon").value(DEFAULT_AVG_SECOND_SERVE_WON.intValue()))
            .andExpect(jsonPath("$.avgServiceGames").value(DEFAULT_AVG_SERVICE_GAMES.intValue()))
            .andExpect(jsonPath("$.avgSavedBreakPoints").value(DEFAULT_AVG_SAVED_BREAK_POINTS.intValue()))
            .andExpect(jsonPath("$.avgFacedBreakPoints").value(DEFAULT_AVG_FACED_BREAK_POINTS.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAvgStat() throws Exception {
        // Get the avgStat
        restAvgStatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAvgStat() throws Exception {
        // Initialize the database
        avgStatRepository.saveAndFlush(avgStat);

        int databaseSizeBeforeUpdate = avgStatRepository.findAll().size();

        // Update the avgStat
        AvgStat updatedAvgStat = avgStatRepository.findById(avgStat.getId()).get();
        // Disconnect from session so that the updates on updatedAvgStat are not directly saved in db
        em.detach(updatedAvgStat);
        updatedAvgStat
            .avgAces(UPDATED_AVG_ACES)
            .avgDoubleFaults(UPDATED_AVG_DOUBLE_FAULTS)
            .avgServicePoints(UPDATED_AVG_SERVICE_POINTS)
            .avgFirstServeIn(UPDATED_AVG_FIRST_SERVE_IN)
            .avgFirstServeWon(UPDATED_AVG_FIRST_SERVE_WON)
            .avgSecondServeWon(UPDATED_AVG_SECOND_SERVE_WON)
            .avgServiceGames(UPDATED_AVG_SERVICE_GAMES)
            .avgSavedBreakPoints(UPDATED_AVG_SAVED_BREAK_POINTS)
            .avgFacedBreakPoints(UPDATED_AVG_FACED_BREAK_POINTS);

        restAvgStatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAvgStat.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAvgStat))
            )
            .andExpect(status().isOk());

        // Validate the AvgStat in the database
        List<AvgStat> avgStatList = avgStatRepository.findAll();
        assertThat(avgStatList).hasSize(databaseSizeBeforeUpdate);
        AvgStat testAvgStat = avgStatList.get(avgStatList.size() - 1);
        assertThat(testAvgStat.getAvgAces()).isEqualTo(UPDATED_AVG_ACES);
        assertThat(testAvgStat.getAvgDoubleFaults()).isEqualTo(UPDATED_AVG_DOUBLE_FAULTS);
        assertThat(testAvgStat.getAvgServicePoints()).isEqualTo(UPDATED_AVG_SERVICE_POINTS);
        assertThat(testAvgStat.getAvgFirstServeIn()).isEqualTo(UPDATED_AVG_FIRST_SERVE_IN);
        assertThat(testAvgStat.getAvgFirstServeWon()).isEqualTo(UPDATED_AVG_FIRST_SERVE_WON);
        assertThat(testAvgStat.getAvgSecondServeWon()).isEqualTo(UPDATED_AVG_SECOND_SERVE_WON);
        assertThat(testAvgStat.getAvgServiceGames()).isEqualTo(UPDATED_AVG_SERVICE_GAMES);
        assertThat(testAvgStat.getAvgSavedBreakPoints()).isEqualTo(UPDATED_AVG_SAVED_BREAK_POINTS);
        assertThat(testAvgStat.getAvgFacedBreakPoints()).isEqualTo(UPDATED_AVG_FACED_BREAK_POINTS);
    }

    @Test
    @Transactional
    void putNonExistingAvgStat() throws Exception {
        int databaseSizeBeforeUpdate = avgStatRepository.findAll().size();
        avgStat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvgStatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avgStat.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avgStat))
            )
            .andExpect(status().isBadRequest());

        // Validate the AvgStat in the database
        List<AvgStat> avgStatList = avgStatRepository.findAll();
        assertThat(avgStatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAvgStat() throws Exception {
        int databaseSizeBeforeUpdate = avgStatRepository.findAll().size();
        avgStat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvgStatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avgStat))
            )
            .andExpect(status().isBadRequest());

        // Validate the AvgStat in the database
        List<AvgStat> avgStatList = avgStatRepository.findAll();
        assertThat(avgStatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAvgStat() throws Exception {
        int databaseSizeBeforeUpdate = avgStatRepository.findAll().size();
        avgStat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvgStatMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avgStat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AvgStat in the database
        List<AvgStat> avgStatList = avgStatRepository.findAll();
        assertThat(avgStatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAvgStatWithPatch() throws Exception {
        // Initialize the database
        avgStatRepository.saveAndFlush(avgStat);

        int databaseSizeBeforeUpdate = avgStatRepository.findAll().size();

        // Update the avgStat using partial update
        AvgStat partialUpdatedAvgStat = new AvgStat();
        partialUpdatedAvgStat.setId(avgStat.getId());

        partialUpdatedAvgStat
            .avgAces(UPDATED_AVG_ACES)
            .avgFirstServeIn(UPDATED_AVG_FIRST_SERVE_IN)
            .avgFirstServeWon(UPDATED_AVG_FIRST_SERVE_WON)
            .avgSecondServeWon(UPDATED_AVG_SECOND_SERVE_WON)
            .avgSavedBreakPoints(UPDATED_AVG_SAVED_BREAK_POINTS)
            .avgFacedBreakPoints(UPDATED_AVG_FACED_BREAK_POINTS);

        restAvgStatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvgStat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvgStat))
            )
            .andExpect(status().isOk());

        // Validate the AvgStat in the database
        List<AvgStat> avgStatList = avgStatRepository.findAll();
        assertThat(avgStatList).hasSize(databaseSizeBeforeUpdate);
        AvgStat testAvgStat = avgStatList.get(avgStatList.size() - 1);
        assertThat(testAvgStat.getAvgAces()).isEqualTo(UPDATED_AVG_ACES);
        assertThat(testAvgStat.getAvgDoubleFaults()).isEqualTo(DEFAULT_AVG_DOUBLE_FAULTS);
        assertThat(testAvgStat.getAvgServicePoints()).isEqualTo(DEFAULT_AVG_SERVICE_POINTS);
        assertThat(testAvgStat.getAvgFirstServeIn()).isEqualTo(UPDATED_AVG_FIRST_SERVE_IN);
        assertThat(testAvgStat.getAvgFirstServeWon()).isEqualTo(UPDATED_AVG_FIRST_SERVE_WON);
        assertThat(testAvgStat.getAvgSecondServeWon()).isEqualTo(UPDATED_AVG_SECOND_SERVE_WON);
        assertThat(testAvgStat.getAvgServiceGames()).isEqualTo(DEFAULT_AVG_SERVICE_GAMES);
        assertThat(testAvgStat.getAvgSavedBreakPoints()).isEqualTo(UPDATED_AVG_SAVED_BREAK_POINTS);
        assertThat(testAvgStat.getAvgFacedBreakPoints()).isEqualTo(UPDATED_AVG_FACED_BREAK_POINTS);
    }

    @Test
    @Transactional
    void fullUpdateAvgStatWithPatch() throws Exception {
        // Initialize the database
        avgStatRepository.saveAndFlush(avgStat);

        int databaseSizeBeforeUpdate = avgStatRepository.findAll().size();

        // Update the avgStat using partial update
        AvgStat partialUpdatedAvgStat = new AvgStat();
        partialUpdatedAvgStat.setId(avgStat.getId());

        partialUpdatedAvgStat
            .avgAces(UPDATED_AVG_ACES)
            .avgDoubleFaults(UPDATED_AVG_DOUBLE_FAULTS)
            .avgServicePoints(UPDATED_AVG_SERVICE_POINTS)
            .avgFirstServeIn(UPDATED_AVG_FIRST_SERVE_IN)
            .avgFirstServeWon(UPDATED_AVG_FIRST_SERVE_WON)
            .avgSecondServeWon(UPDATED_AVG_SECOND_SERVE_WON)
            .avgServiceGames(UPDATED_AVG_SERVICE_GAMES)
            .avgSavedBreakPoints(UPDATED_AVG_SAVED_BREAK_POINTS)
            .avgFacedBreakPoints(UPDATED_AVG_FACED_BREAK_POINTS);

        restAvgStatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvgStat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvgStat))
            )
            .andExpect(status().isOk());

        // Validate the AvgStat in the database
        List<AvgStat> avgStatList = avgStatRepository.findAll();
        assertThat(avgStatList).hasSize(databaseSizeBeforeUpdate);
        AvgStat testAvgStat = avgStatList.get(avgStatList.size() - 1);
        assertThat(testAvgStat.getAvgAces()).isEqualTo(UPDATED_AVG_ACES);
        assertThat(testAvgStat.getAvgDoubleFaults()).isEqualTo(UPDATED_AVG_DOUBLE_FAULTS);
        assertThat(testAvgStat.getAvgServicePoints()).isEqualTo(UPDATED_AVG_SERVICE_POINTS);
        assertThat(testAvgStat.getAvgFirstServeIn()).isEqualTo(UPDATED_AVG_FIRST_SERVE_IN);
        assertThat(testAvgStat.getAvgFirstServeWon()).isEqualTo(UPDATED_AVG_FIRST_SERVE_WON);
        assertThat(testAvgStat.getAvgSecondServeWon()).isEqualTo(UPDATED_AVG_SECOND_SERVE_WON);
        assertThat(testAvgStat.getAvgServiceGames()).isEqualTo(UPDATED_AVG_SERVICE_GAMES);
        assertThat(testAvgStat.getAvgSavedBreakPoints()).isEqualTo(UPDATED_AVG_SAVED_BREAK_POINTS);
        assertThat(testAvgStat.getAvgFacedBreakPoints()).isEqualTo(UPDATED_AVG_FACED_BREAK_POINTS);
    }

    @Test
    @Transactional
    void patchNonExistingAvgStat() throws Exception {
        int databaseSizeBeforeUpdate = avgStatRepository.findAll().size();
        avgStat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvgStatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, avgStat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avgStat))
            )
            .andExpect(status().isBadRequest());

        // Validate the AvgStat in the database
        List<AvgStat> avgStatList = avgStatRepository.findAll();
        assertThat(avgStatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAvgStat() throws Exception {
        int databaseSizeBeforeUpdate = avgStatRepository.findAll().size();
        avgStat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvgStatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avgStat))
            )
            .andExpect(status().isBadRequest());

        // Validate the AvgStat in the database
        List<AvgStat> avgStatList = avgStatRepository.findAll();
        assertThat(avgStatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAvgStat() throws Exception {
        int databaseSizeBeforeUpdate = avgStatRepository.findAll().size();
        avgStat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvgStatMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avgStat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AvgStat in the database
        List<AvgStat> avgStatList = avgStatRepository.findAll();
        assertThat(avgStatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAvgStat() throws Exception {
        // Initialize the database
        avgStatRepository.saveAndFlush(avgStat);

        int databaseSizeBeforeDelete = avgStatRepository.findAll().size();

        // Delete the avgStat
        restAvgStatMockMvc
            .perform(delete(ENTITY_API_URL_ID, avgStat.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AvgStat> avgStatList = avgStatRepository.findAll();
        assertThat(avgStatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
