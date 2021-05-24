package com.bricola.cocovoit.repository;

import com.bricola.cocovoit.domain.Path;
import com.bricola.cocovoit.domain.enumeration.PathType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
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
     * Recherche des trajets "Aller" par date avec des places disponibles (count sur le nombre de reservations par trajet)
     *
     * @param departurePlace
     * @param date
     * @return
     */
    @Query(value = "SELECT p.* FROM `path` p " +
        "LEFT JOIN " +
        "    (SELECT COUNT(r.path_id) AS nbRegistrations, r.path_id " +
        "    FROM registration r " +
        "    GROUP BY r.path_id) AS R " +
        "ON p.id = R.path_id " +
        "WHERE (nbRegistrations IS NULL OR nbRegistrations < p.number_of_passengers) " +
        "AND p.departure_place LIKE %:departurePlace% " +
        "AND p.date >= :date " +
        "ORDER BY p.date",
        nativeQuery = true)
    List<Path> findAllAvailableByDeparturePlace(@Param("departurePlace") String departurePlace,
                                                @Param("date") Instant date);

    /**
     * Recherche des trajets "Retour" par date avec des places disponibles (count sur le nombre de reservations par trajet)
     *
     * @param arrivalPlace
     * @param date
     * @return
     */
    @Query(value = "SELECT p.* FROM `path` p " +
        "LEFT JOIN " +
        "    (SELECT COUNT(r.path_id) AS nbRegistrations, r.path_id " +
        "    FROM registration r " +
        "    GROUP BY r.path_id) AS R " +
        "ON p.id = R.path_id " +
        "WHERE (nbRegistrations IS NULL OR nbRegistrations < p.number_of_passengers) " +
        "AND p.arrival_place LIKE %:arrivalPlace% " +
        "AND p.date >= :date " +
        "ORDER BY p.date",
        nativeQuery = true)
    List<Path> findAllAvailableByArrivalPlace(@Param("arrivalPlace") String arrivalPlace,
                                                                        @Param("date") Instant date);

    /**
     * Recherche des trajets ayant le même créateur
     *
     * @param userId Identifiant de l'utilisateur courant
     * @return Liste de trajet
     */
    List<Path> findAllByUserId(Long userId);

}
