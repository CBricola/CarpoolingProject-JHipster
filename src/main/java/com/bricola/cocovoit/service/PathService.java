package com.bricola.cocovoit.service;

import com.bricola.cocovoit.domain.Path;
import com.bricola.cocovoit.domain.enumeration.PathType;
import com.bricola.cocovoit.repository.PathRepository;

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
     *
     * @param pathType
     * @param departurePlace
     * @param arrivalPlace
     * @param pathDate
     * @return
     */
    public List<Path> findAllBySearchCriteria(PathType pathType, String departurePlace, String arrivalPlace, String pathDate){
        return null;
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
