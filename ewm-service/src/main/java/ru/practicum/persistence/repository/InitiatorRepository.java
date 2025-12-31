package main.java.ru.practicum.persistence.repository;

import main.java.ru.practicum.persistence.entity.Initiator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InitiatorRepository extends JpaRepository<Initiator, Long> {
    @Query(nativeQuery = true, value = """
            SELECT *
            FROM initiators
            WHERE id = ANY(:ids)
            """)
    List<Initiator> getInitiatorByCompilationIds(@Param("ids") Long[] ids);
}
