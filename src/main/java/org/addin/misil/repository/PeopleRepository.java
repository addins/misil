package org.addin.misil.repository;

import org.addin.misil.domain.People;
import org.addin.misil.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data JPA repository for the People entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeopleRepository extends JpaRepository<People,Long> {

    Optional<People> findOneByUser(User user);
}
