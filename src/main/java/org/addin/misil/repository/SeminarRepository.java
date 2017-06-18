package org.addin.misil.repository;

import org.addin.misil.domain.Seminar;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Seminar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeminarRepository extends JpaRepository<Seminar,Long> {
    
    @Query("select distinct seminar from Seminar seminar left join fetch seminar.attendees left join fetch seminar.specialGuests left join fetch seminar.tags")
    List<Seminar> findAllWithEagerRelationships();

    @Query("select seminar from Seminar seminar left join fetch seminar.attendees left join fetch seminar.specialGuests left join fetch seminar.tags where seminar.id =:id")
    Seminar findOneWithEagerRelationships(@Param("id") Long id);
    
}
