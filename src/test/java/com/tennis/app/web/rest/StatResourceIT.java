package com.tennis.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tennis.app.IntegrationTest;
import com.tennis.app.domain.Stat;
import com.tennis.app.repository.StatRepository;
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
 * Integration tests for the {@link StatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StatResourceIT {

    private static final Long DEFAULT_ACES = 1L;
    private static final Long UPDATED_ACES = 2L;

    private static final Long DEFAULT_DOUBLE_FAULTS = 1L;
    private static final Long UPDATED_DOUBLE_FAULTS = 2L;

    private static final Long DEFAULT_SERVICE_POINTS = 1L;
    private static final Long UPDATED_SERVICE_POINTS = 2L;

    private static final Long DEFAULT_FIRST_SERVE_IN = 1L;
    private static final Long UPDATED_FIRST_SERVE_IN = 2L;

    private static final Long DEFAULT_FIRST_SERVE_WON = 1L;
    private static final Long UPDATED_FIRST_SERVE_WON = 2L;

    private static final Long DEFAULT_SECOND_SERVE_WON = 1L;
    private static final Long UPDATED_SECOND_SERVE_WON = 2L;

    private static final Long DEFAULT_SERVICE_GAMES = 1L;
    private static final Long UPDATED_SERVICE_GAMES = 2L;

    private static final Long DEFAULT_SAVED_BREAK_POINTS = 1L;
    private static final Long UPDATED_SAVED_BREAK_POINTS = 2L;

    private static final Long DEFAULT_FACED_BREAK_POINTS = 1L;
    private static final Long UPDATED_FACED_BREAK_POINTS = 2L;

    private static final String ENTITY_API_URL = "/api/stats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StatRepository statRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatMockMvc;

    private Stat stat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stat createEntity(EntityManager em) {
        Stat stat = new Stat()
            .aces(DEFAULT_ACES)
            .doubleFaults(DEFAULT_DOUBLE_FAULTS)
            .servicePoints(DEFAULT_SERVICE_POINTS)
            .firstServeIn(DEFAULT_FIRST_SERVE_IN)
            .firstServeWon(DEFAULT_FIRST_SERVE_WON)
            .secondServeWon(DEFAULT_SECOND_SERVE_WON)
            .serviceGames(DEFAULT_SERVICE_GAMES)
            .savedBreakPoints(DEFAULT_SAVED_BREAK_POINTS)
            .facedBreakPoints(DEFAULT_FACED_BREAK_POINTS);
        return stat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stat createUpdatedEntity(EntityManager em) {
        Stat stat = new Stat()
            .aces(UPDATED_ACES)
            .doubleFaults(UPDATED_DOUBLE_FAULTS)
            .servicePoints(UPDATED_SERVICE_POINTS)
            .firstServeIn(UPDATED_FIRST_SERVE_IN)
            .firstServeWon(UPDATED_FIRST_SERVE_WON)
            .secondServeWon(UPDATED_SECOND_SERVE_WON)
            .serviceGames(UPDATED_SERVICE_GAMES)
            .savedBreakPoints(UPDATED_SAVED_BREAK_POINTS)
            .facedBreakPoints(UPDATED_FACED_BREAK_POINTS);
        return stat;
    }

    @BeforeEach
    public void initTest() {
        stat = createEntity(em);
    }

    @Test
    @Transactional
    void createStat() throws Exception {
        int databaseSizeBeforeCreate = statRepository.findAll().size();
        // Create the Stat
        restStatMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stat))
            )
            .andExpect(status().isCreated());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeCreate + 1);
        Stat testStat = statList.get(statList.size() - 1);
        assertThat(testStat.getAces()).isEqualTo(DEFAULT_ACES);
        assertThat(testStat.getDoubleFaults()).isEqualTo(DEFAULT_DOUBLE_FAULTS);
        assertThat(testStat.getServicePoints()).isEqualTo(DEFAULT_SERVICE_POINTS);
        assertThat(testStat.getFirstServeIn()).isEqualTo(DEFAULT_FIRST_SERVE_IN);
        assertThat(testStat.getFirstServeWon()).isEqualTo(DEFAULT_FIRST_SERVE_WON);
        assertThat(testStat.getSecondServeWon()).isEqualTo(DEFAULT_SECOND_SERVE_WON);
        assertThat(testStat.getServiceGames()).isEqualTo(DEFAULT_SERVICE_GAMES);
        assertThat(testStat.getSavedBreakPoints()).isEqualTo(DEFAULT_SAVED_BREAK_POINTS);
        assertThat(testStat.getFacedBreakPoints()).isEqualTo(DEFAULT_FACED_BREAK_POINTS);
    }

    @Test
    @Transactional
    void createStatWithExistingId() throws Exception {
        // Create the Stat with an existing ID
        stat.setId(1L);

        int databaseSizeBeforeCreate = statRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStats() throws Exception {
        // Initialize the database
        statRepository.saveAndFlush(stat);

        // Get all the statList
        restStatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stat.getId().intValue())))
            .andExpect(jsonPath("$.[*].aces").value(hasItem(DEFAULT_ACES.intValue())))
            .andExpect(jsonPath("$.[*].doubleFaults").value(hasItem(DEFAULT_DOUBLE_FAULTS.intValue())))
            .andExpect(jsonPath("$.[*].servicePoints").value(hasItem(DEFAULT_SERVICE_POINTS.intValue())))
            .andExpect(jsonPath("$.[*].firstServeIn").value(hasItem(DEFAULT_FIRST_SERVE_IN.intValue())))
            .andExpect(jsonPath("$.[*].firstServeWon").value(hasItem(DEFAULT_FIRST_SERVE_WON.intValue())))
            .andExpect(jsonPath("$.[*].secondServeWon").value(hasItem(DEFAULT_SECOND_SERVE_WON.intValue())))
            .andExpect(jsonPath("$.[*].serviceGames").value(hasItem(DEFAULT_SERVICE_GAMES.intValue())))
            .andExpect(jsonPath("$.[*].savedBreakPoints").value(hasItem(DEFAULT_SAVED_BREAK_POINTS.intValue())))
            .andExpect(jsonPath("$.[*].facedBreakPoints").value(hasItem(DEFAULT_FACED_BREAK_POINTS.intValue())));
    }

    @Test
    @Transactional
    void getStat() throws Exception {
        // Initialize the database
        statRepository.saveAndFlush(stat);

        // Get the stat
        restStatMockMvc
            .perform(get(ENTITY_API_URL_ID, stat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stat.getId().intValue()))
            .andExpect(jsonPath("$.aces").value(DEFAULT_ACES.intValue()))
            .andExpect(jsonPath("$.doubleFaults").value(DEFAULT_DOUBLE_FAULTS.intValue()))
            .andExpect(jsonPath("$.servicePoints").value(DEFAULT_SERVICE_POINTS.intValue()))
            .andExpect(jsonPath("$.firstServeIn").value(DEFAULT_FIRST_SERVE_IN.intValue()))
            .andExpect(jsonPath("$.firstServeWon").value(DEFAULT_FIRST_SERVE_WON.intValue()))
            .andExpect(jsonPath("$.secondServeWon").value(DEFAULT_SECOND_SERVE_WON.intValue()))
            .andExpect(jsonPath("$.serviceGames").value(DEFAULT_SERVICE_GAMES.intValue()))
            .andExpect(jsonPath("$.savedBreakPoints").value(DEFAULT_SAVED_BREAK_POINTS.intValue()))
            .andExpect(jsonPath("$.facedBreakPoints").value(DEFAULT_FACED_BREAK_POINTS.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingStat() throws Exception {
        // Get the stat
        restStatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStat() throws Exception {
        // Initialize the database
        statRepository.saveAndFlush(stat);

        int databaseSizeBeforeUpdate = statRepository.findAll().size();

        // Update the stat
        Stat updatedStat = statRepository.findById(stat.getId()).get();
        // Disconnect from session so that the updates on updatedStat are not directly saved in db
        em.detach(updatedStat);
        updatedStat
            .aces(UPDATED_ACES)
            .doubleFaults(UPDATED_DOUBLE_FAULTS)
            .servicePoints(UPDATED_SERVICE_POINTS)
            .firstServeIn(UPDATED_FIRST_SERVE_IN)
            .firstServeWon(UPDATED_FIRST_SERVE_WON)
            .secondServeWon(UPDATED_SECOND_SERVE_WON)
            .serviceGames(UPDATED_SERVICE_GAMES)
            .savedBreakPoints(UPDATED_SAVED_BREAK_POINTS)
            .facedBreakPoints(UPDATED_FACED_BREAK_POINTS);

        restStatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStat.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStat))
            )
            .andExpect(status().isOk());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeUpdate);
        Stat testStat = statList.get(statList.size() - 1);
        assertThat(testStat.getAces()).isEqualTo(UPDATED_ACES);
        assertThat(testStat.getDoubleFaults()).isEqualTo(UPDATED_DOUBLE_FAULTS);
        assertThat(testStat.getServicePoints()).isEqualTo(UPDATED_SERVICE_POINTS);
        assertThat(testStat.getFirstServeIn()).isEqualTo(UPDATED_FIRST_SERVE_IN);
        assertThat(testStat.getFirstServeWon()).isEqualTo(UPDATED_FIRST_SERVE_WON);
        assertThat(testStat.getSecondServeWon()).isEqualTo(UPDATED_SECOND_SERVE_WON);
        assertThat(testStat.getServiceGames()).isEqualTo(UPDATED_SERVICE_GAMES);
        assertThat(testStat.getSavedBreakPoints()).isEqualTo(UPDATED_SAVED_BREAK_POINTS);
        assertThat(testStat.getFacedBreakPoints()).isEqualTo(UPDATED_FACED_BREAK_POINTS);
    }

    @Test
    @Transactional
    void putNonExistingStat() throws Exception {
        int databaseSizeBeforeUpdate = statRepository.findAll().size();
        stat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stat.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStat() throws Exception {
        int databaseSizeBeforeUpdate = statRepository.findAll().size();
        stat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStat() throws Exception {
        int databaseSizeBeforeUpdate = statRepository.findAll().size();
        stat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatWithPatch() throws Exception {
        // Initialize the database
        statRepository.saveAndFlush(stat);

        int databaseSizeBeforeUpdate = statRepository.findAll().size();

        // Update the stat using partial update
        Stat partialUpdatedStat = new Stat();
        partialUpdatedStat.setId(stat.getId());

        partialUpdatedStat.servicePoints(UPDATED_SERVICE_POINTS).facedBreakPoints(UPDATED_FACED_BREAK_POINTS);

        restStatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStat))
            )
            .andExpect(status().isOk());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeUpdate);
        Stat testStat = statList.get(statList.size() - 1);
        assertThat(testStat.getAces()).isEqualTo(DEFAULT_ACES);
        assertThat(testStat.getDoubleFaults()).isEqualTo(DEFAULT_DOUBLE_FAULTS);
        assertThat(testStat.getServicePoints()).isEqualTo(UPDATED_SERVICE_POINTS);
        assertThat(testStat.getFirstServeIn()).isEqualTo(DEFAULT_FIRST_SERVE_IN);
        assertThat(testStat.getFirstServeWon()).isEqualTo(DEFAULT_FIRST_SERVE_WON);
        assertThat(testStat.getSecondServeWon()).isEqualTo(DEFAULT_SECOND_SERVE_WON);
        assertThat(testStat.getServiceGames()).isEqualTo(DEFAULT_SERVICE_GAMES);
        assertThat(testStat.getSavedBreakPoints()).isEqualTo(DEFAULT_SAVED_BREAK_POINTS);
        assertThat(testStat.getFacedBreakPoints()).isEqualTo(UPDATED_FACED_BREAK_POINTS);
    }

    @Test
    @Transactional
    void fullUpdateStatWithPatch() throws Exception {
        // Initialize the database
        statRepository.saveAndFlush(stat);

        int databaseSizeBeforeUpdate = statRepository.findAll().size();

        // Update the stat using partial update
        Stat partialUpdatedStat = new Stat();
        partialUpdatedStat.setId(stat.getId());

        partialUpdatedStat
            .aces(UPDATED_ACES)
            .doubleFaults(UPDATED_DOUBLE_FAULTS)
            .servicePoints(UPDATED_SERVICE_POINTS)
            .firstServeIn(UPDATED_FIRST_SERVE_IN)
            .firstServeWon(UPDATED_FIRST_SERVE_WON)
            .secondServeWon(UPDATED_SECOND_SERVE_WON)
            .serviceGames(UPDATED_SERVICE_GAMES)
            .savedBreakPoints(UPDATED_SAVED_BREAK_POINTS)
            .facedBreakPoints(UPDATED_FACED_BREAK_POINTS);

        restStatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStat))
            )
            .andExpect(status().isOk());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeUpdate);
        Stat testStat = statList.get(statList.size() - 1);
        assertThat(testStat.getAces()).isEqualTo(UPDATED_ACES);
        assertThat(testStat.getDoubleFaults()).isEqualTo(UPDATED_DOUBLE_FAULTS);
        assertThat(testStat.getServicePoints()).isEqualTo(UPDATED_SERVICE_POINTS);
        assertThat(testStat.getFirstServeIn()).isEqualTo(UPDATED_FIRST_SERVE_IN);
        assertThat(testStat.getFirstServeWon()).isEqualTo(UPDATED_FIRST_SERVE_WON);
        assertThat(testStat.getSecondServeWon()).isEqualTo(UPDATED_SECOND_SERVE_WON);
        assertThat(testStat.getServiceGames()).isEqualTo(UPDATED_SERVICE_GAMES);
        assertThat(testStat.getSavedBreakPoints()).isEqualTo(UPDATED_SAVED_BREAK_POINTS);
        assertThat(testStat.getFacedBreakPoints()).isEqualTo(UPDATED_FACED_BREAK_POINTS);
    }

    @Test
    @Transactional
    void patchNonExistingStat() throws Exception {
        int databaseSizeBeforeUpdate = statRepository.findAll().size();
        stat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStat() throws Exception {
        int databaseSizeBeforeUpdate = statRepository.findAll().size();
        stat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStat() throws Exception {
        int databaseSizeBeforeUpdate = statRepository.findAll().size();
        stat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStat() throws Exception {
        // Initialize the database
        statRepository.saveAndFlush(stat);

        int databaseSizeBeforeDelete = statRepository.findAll().size();

        // Delete the stat
        restStatMockMvc
            .perform(delete(ENTITY_API_URL_ID, stat.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
