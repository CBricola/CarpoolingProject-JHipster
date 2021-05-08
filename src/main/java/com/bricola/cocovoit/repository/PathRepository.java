package com.bricola.cocovoit.repository;

import com.bricola.cocovoit.domain.Path;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Path entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PathRepository extends JpaRepository<Path, Long> {}
