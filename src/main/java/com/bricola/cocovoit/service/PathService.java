package com.bricola.cocovoit.service;

import com.bricola.cocovoit.domain.Path;
import com.bricola.cocovoit.domain.enumeration.PathType;
import com.bricola.cocovoit.repository.PathRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Path}.
 */
@Service
@Transactional
public class PathService {

    private final Logger log = LoggerFactory.getLogger(PathService.class);

    private final PathRepository pathRepository;

    public PathService(PathRepository pathRepository) {
        this.pathRepository = pathRepository;
    }

    /**
     * Save a path.
     *
     * @param path the entity to save.
     * @return the persisted entity.
     */
    public Path save(Path path) {
        log.debug("Request to save Path : {}", path);
        return pathRepository.save(path);
    }

    /**
     * Partially update a path.
     *
     * @param path the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Path> partialUpdate(Path path) {
        log.debug("Request to partially update Path : {}", path);

        return pathRepository
            .findById(path.getId())
            .map(
                existingPath -> {
                    if (path.getDate() != null) {
                        existingPath.setDate(path.getDate());
                    }
                    if (path.getNumberOfPassengers() != null) {
                        existingPath.setNumberOfPassengers(path.getNumberOfPassengers());
                    }
                    if (path.getDeparturePlace() != null) {
                        existingPath.setDeparturePlace(path.getDeparturePlace());
                    }
                    if (path.getArrivalPlace() != null) {
                        existingPath.setArrivalPlace(path.getArrivalPlace());
                    }

                    return existingPath;
                }
            )
            .map(pathRepository::save);
    }

    /**
     * Get all the paths.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Path> findAll(Pageable pageable) {
        log.debug("Request to get all Paths");
        return pathRepository.findAll(pageable);
    }

    /**
     * Retourne une liste de trajets correspondant aux critères de recherche
     *
     * @param pathType
     * @param departurePlace
     * @param arrivalPlace
     * @param pathDateString date au format String
     * @return
     */
    public List<Path> findAllBySearchCriteria(String pathType, String departurePlace, String arrivalPlace, String pathDateString) throws ParseException {

        // Parser au format "Instant" la date du trajet
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Instant pathDateInstant = format.parse(pathDateString).toInstant();

        // Recherche selon le type de trajet ("Aller" ou "Retour")
        List<Path> paths = null;
        if (pathType.equals(PathType.ALLER.getLabel())) {
            paths = pathRepository.findAllByDeparturePlaceLikeAndDateIsGreaterThanEqual(departurePlace, pathDateInstant);
        } else {
            paths = pathRepository.findAllByArrivalPlaceLikeAndDateIsGreaterThanEqual(arrivalPlace, pathDateInstant);
        }

        return paths;
    }

    /**
     * Get one path by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Path> findOne(Long id) {
        log.debug("Request to get Path : {}", id);
        return pathRepository.findById(id);
    }

    /**
     * Delete the path by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Path : {}", id);
        pathRepository.deleteById(id);
    }
}
