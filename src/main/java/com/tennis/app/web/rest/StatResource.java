package com.tennis.app.web.rest;

import com.tennis.app.domain.Stat;
import com.tennis.app.repository.StatRepository;
import com.tennis.app.service.StatService;
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
 * REST controller for managing {@link com.tennis.app.domain.Stat}.
 */
@RestController
@RequestMapping("/api")
public class StatResource {

    private final Logger log = LoggerFactory.getLogger(StatResource.class);

    private static final String ENTITY_NAME = "stat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatService statService;

    private final StatRepository statRepository;

    public StatResource(StatService statService, StatRepository statRepository) {
        this.statService = statService;
        this.statRepository = statRepository;
    }

    /**
     * {@code POST  /stats} : Create a new stat.
     *
     * @param stat the stat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stat, or with status {@code 400 (Bad Request)} if the stat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stats")
    public ResponseEntity<Stat> createStat(@RequestBody Stat stat) throws URISyntaxException {
        log.debug("REST request to save Stat : {}", stat);
        if (stat.getId() != null) {
            throw new BadRequestAlertException("A new stat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Stat result = statService.save(stat);
        return ResponseEntity
            .created(new URI("/api/stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stats/:id} : Updates an existing stat.
     *
     * @param id the id of the stat to save.
     * @param stat the stat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stat,
     * or with status {@code 400 (Bad Request)} if the stat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stats/{id}")
    public ResponseEntity<Stat> updateStat(@PathVariable(value = "id", required = false) final Long id, @RequestBody Stat stat)
        throws URISyntaxException {
        log.debug("REST request to update Stat : {}, {}", id, stat);
        if (stat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Stat result = statService.update(stat);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stat.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /stats/:id} : Partial updates given fields of an existing stat, field will ignore if it is null
     *
     * @param id the id of the stat to save.
     * @param stat the stat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stat,
     * or with status {@code 400 (Bad Request)} if the stat is not valid,
     * or with status {@code 404 (Not Found)} if the stat is not found,
     * or with status {@code 500 (Internal Server Error)} if the stat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/stats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Stat> partialUpdateStat(@PathVariable(value = "id", required = false) final Long id, @RequestBody Stat stat)
        throws URISyntaxException {
        log.debug("REST request to partial update Stat partially : {}, {}", id, stat);
        if (stat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Stat> result = statService.partialUpdate(stat);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stat.getId().toString())
        );
    }

    /**
     * {@code GET  /stats} : get all the stats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stats in body.
     */
    @GetMapping("/stats")
    public ResponseEntity<List<Stat>> getAllStats(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Stats");
        Page<Stat> page = statService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stats/:id} : get the "id" stat.
     *
     * @param id the id of the stat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stats/{id}")
    public ResponseEntity<Stat> getStat(@PathVariable Long id) {
        log.debug("REST request to get Stat : {}", id);
        Optional<Stat> stat = statService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stat);
    }

    /**
     * {@code DELETE  /stats/:id} : delete the "id" stat.
     *
     * @param id the id of the stat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stats/{id}")
    public ResponseEntity<Void> deleteStat(@PathVariable Long id) {
        log.debug("REST request to delete Stat : {}", id);
        statService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
