package com.bricola.cocovoit.repository;

import com.bricola.cocovoit.domain.Path;
import com.bricola.cocovoit.domain.Registration;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Registration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
	
	
	
	 /**
     * Effacement des enregistrements ayant le même id de trajet
     * @param id du trajet
     * @return
     */
    @Query(value = "DELETE FROM registration WHERE path_id = :idPath", nativeQuery = true)
    void deleteByPathId(@Param("idPath") Long idPath);

	
	
	
	
}
