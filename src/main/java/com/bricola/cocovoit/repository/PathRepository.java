package com.bricola.cocovoit.repository;

import com.bricola.cocovoit.domain.Path;
import com.bricola.cocovoit.domain.enumeration.PathType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Spring Data SQL repository for the Path entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PathRepository extends JpaRepository<Path, Long> {

    /**
     * Recherche des trajets "Aller" par date
     * @param departurePlace
     * @param date
     * @return
     */
    List<Path> findAllByDeparturePlaceLikeAndDateIsGreaterThanEqual(String departurePlace, Instant date);
    

    /**
     * Recherche des trajets "Retour" par date
     * @param arrivalPlace
     * @param date
     * @return
     */
    List<Path> findAllByArrivalPlaceLikeAndDateIsGreaterThanEqual(String arrivalPlace, Instant date);
    
    /**
     * Recherche des trajets ayant le même créateur
     * @param userId Identifiant de l'utilisateur courant
     * @return Liste de trajet
     */
    List<Path> findAllByUserId(Long userId);
    
}
