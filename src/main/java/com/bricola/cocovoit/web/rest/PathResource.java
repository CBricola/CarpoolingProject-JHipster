package com.bricola.cocovoit.web.rest;

import com.bricola.cocovoit.domain.Path;
import com.bricola.cocovoit.domain.enumeration.PathType;
import com.bricola.cocovoit.repository.PathRepository;
import com.bricola.cocovoit.service.PathService;
import com.bricola.cocovoit.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
 * REST controller for managing {@link com.bricola.cocovoit.domain.Path}.
 */
@RestController
@RequestMapping("/api")
public class PathResource {

    private final Logger log = LoggerFactory.getLogger(PathResource.class);

    private static final String ENTITY_NAME = "path";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PathService pathService;

    private final PathRepository pathRepository;

    public PathResource(PathService pathService, PathRepository pathRepository) {
        this.pathService = pathService;
        this.pathRepository = pathRepository;
    }

    /**
     * {@code POST  /paths} : Create a new path.
     *
     * @param path the path to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new path, or with status {@code 400 (Bad Request)} if the path has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paths")
    public ResponseEntity<Path> createPath(@Valid @RequestBody Path path) throws URISyntaxException {
        log.debug("REST request to save Path : {}", path);
        if (path.getId() != null) {
            throw new BadRequestAlertException("A new path cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Path result = pathService.save(path);
        return ResponseEntity
            .created(new URI("/api/paths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paths/:id} : Updates an existing path.
     *
     * @param id   the id of the path to save.
     * @param path the path to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated path,
     * or with status {@code 400 (Bad Request)} if the path is not valid,
     * or with status {@code 500 (Internal Server Error)} if the path couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paths/{id}")
    public ResponseEntity<Path> updatePath(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Path path)
        throws URISyntaxException {
        log.debug("REST request to update Path : {}, {}", id, path);
        if (path.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, path.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pathRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Path result = pathService.save(path);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, path.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /paths/:id} : Partial updates given fields of an existing path, field will ignore if it is null
     *
     * @param id   the id of the path to save.
     * @param path the path to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated path,
     * or with status {@code 400 (Bad Request)} if the path is not valid,
     * or with status {@code 404 (Not Found)} if the path is not found,
     * or with status {@code 500 (Internal Server Error)} if the path couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/paths/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Path> partialUpdatePath(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Path path
    ) throws URISyntaxException {
        log.debug("REST request to partial update Path partially : {}, {}", id, path);
        if (path.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, path.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pathRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Path> result = pathService.partialUpdate(path);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, path.getId().toString())
        );
    }

    /**
     * {@code GET  /paths} : get all the paths.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paths in body.
     */
    @GetMapping("/paths")
    public ResponseEntity<List<Path>> getAllPaths(Pageable pageable) {
        log.debug("REST request to get a page of Paths");
        Page<Path> page = pathService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /paths/search} : Retourne une liste de trajets correspondant aux critères de recherche
     *
     * @param pathType       type de trajet ("aller" ou "retour")
     * @param departurePlace ville de départ
     * @param arrivalPlace   ville d'arrivée
     * @param pathDate       date du trajet
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paths in body.
     */
    @GetMapping("/paths/search")
    public ResponseEntity<List<Path>> getAllPathsBySearchCriteria(@RequestParam("type") String pathType,
                                                                  @RequestParam("departure") String departurePlace,
                                                                  @RequestParam("arrival") String arrivalPlace,
                                                                  @RequestParam("date") String pathDate) throws ParseException {

        log.debug("Requete GET pour obtenir une liste de trajets par critères de recherche");
        log.debug(">>>>> pathType : " + pathType);
        log.debug(">>>>> pathDate : " + pathDate);
//        Page<Path> page = pathService.findAll(pageable);
        List<Path> paths = pathService.findAllBySearchCriteria(pathType, departurePlace, arrivalPlace, pathDate);

//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return new ResponseEntity<>(paths, HttpStatus.OK);
//        return ResponseEntity.ok().headers(headers).body(paths);
    }
    
    @PostMapping("/paths/search")
    public ResponseEntity<List<Path>> getAllPathsByUserId(@PathVariable Long userId) throws ParseException {

        log.debug("Requete GET pour obtenir une liste de trajets de l'utilisateur courant");
        log.debug(">>>>> userId : " + userId);
        
//        Page<Path> page = pathService.findAll(pageable);
        List<Path> paths = pathService.findAllByUserId(userId);

//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return new ResponseEntity<>(paths, HttpStatus.OK);
//        return ResponseEntity.ok().headers(headers).body(paths);
    }

    /**
     * {@code GET  /paths/:id} : get the "id" path.
     *
     * @param id the id of the path to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the path, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paths/{id}")
    public ResponseEntity<Path> getPath(@PathVariable Long id) {
        log.debug("REST request to get Path : {}", id);
        Optional<Path> path = pathService.findOne(id);
        return ResponseUtil.wrapOrNotFound(path);
    }

    /**
     * {@code DELETE  /paths/:id} : delete the "id" path.
     *
     * @param id the id of the path to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paths/{id}")
    public ResponseEntity<Void> deletePath(@PathVariable Long id) {
        log.debug("REST request to delete Path : {}", id);
        pathService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
