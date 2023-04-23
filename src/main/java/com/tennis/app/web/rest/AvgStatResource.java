package com.tennis.app.web.rest;

import com.tennis.app.domain.AvgStat;
import com.tennis.app.repository.AvgStatRepository;
import com.tennis.app.service.AvgStatService;
import com.tennis.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tennis.app.domain.AvgStat}.
 */
@RestController
@RequestMapping("/api")
public class AvgStatResource {

    private final Logger log = LoggerFactory.getLogger(AvgStatResource.class);

    private static final String ENTITY_NAME = "avgStat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvgStatService avgStatService;

    private final AvgStatRepository avgStatRepository;

    public AvgStatResource(AvgStatService avgStatService, AvgStatRepository avgStatRepository) {
        this.avgStatService = avgStatService;
        this.avgStatRepository = avgStatRepository;
    }

    /**
     * {@code POST  /avg-stats} : Create a new avgStat.
     *
     * @param avgStat the avgStat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avgStat, or with status {@code 400 (Bad Request)} if the avgStat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avg-stats")
    public ResponseEntity<AvgStat> createAvgStat(@RequestBody AvgStat avgStat) throws URISyntaxException {
        log.debug("REST request to save AvgStat : {}", avgStat);
        if (avgStat.getId() != null) {
            throw new BadRequestAlertException("A new avgStat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvgStat result = avgStatService.save(avgStat);
        return ResponseEntity
            .created(new URI("/api/avg-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avg-stats/:id} : Updates an existing avgStat.
     *
     * @param id the id of the avgStat to save.
     * @param avgStat the avgStat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avgStat,
     * or with status {@code 400 (Bad Request)} if the avgStat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avgStat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avg-stats/{id}")
    public ResponseEntity<AvgStat> updateAvgStat(@PathVariable(value = "id", required = false) final Long id, @RequestBody AvgStat avgStat)
        throws URISyntaxException {
        log.debug("REST request to update AvgStat : {}, {}", id, avgStat);
        if (avgStat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avgStat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avgStatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AvgStat result = avgStatService.update(avgStat);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avgStat.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /avg-stats/:id} : Partial updates given fields of an existing avgStat, field will ignore if it is null
     *
     * @param id the id of the avgStat to save.
     * @param avgStat the avgStat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avgStat,
     * or with status {@code 400 (Bad Request)} if the avgStat is not valid,
     * or with status {@code 404 (Not Found)} if the avgStat is not found,
     * or with status {@code 500 (Internal Server Error)} if the avgStat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/avg-stats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AvgStat> partialUpdateAvgStat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AvgStat avgStat
    ) throws URISyntaxException {
        log.debug("REST request to partial update AvgStat partially : {}, {}", id, avgStat);
        if (avgStat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avgStat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avgStatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AvgStat> result = avgStatService.partialUpdate(avgStat);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avgStat.getId().toString())
        );
    }

    /**
     * {@code GET  /avg-stats} : get all the avgStats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avgStats in body.
     */
    @GetMapping("/avg-stats")
    public ResponseEntity<List<AvgStat>> getAllAvgStats(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AvgStats");
        Page<AvgStat> page = avgStatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /avg-stats/:id} : get the "id" avgStat.
     *
     * @param id the id of the avgStat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avgStat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avg-stats/{id}")
    public ResponseEntity<AvgStat> getAvgStat(@PathVariable Long id) {
        log.debug("REST request to get AvgStat : {}", id);
        Optional<AvgStat> avgStat = avgStatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avgStat);
    }

    /**
     * {@code DELETE  /avg-stats/:id} : delete the "id" avgStat.
     *
     * @param id the id of the avgStat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avg-stats/{id}")
    public ResponseEntity<Void> deleteAvgStat(@PathVariable Long id) {
        log.debug("REST request to delete AvgStat : {}", id);
        avgStatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
