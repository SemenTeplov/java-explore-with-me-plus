package main.java.ru.practicum.persistence.repository;


import main.java.ru.practicum.persistence.entity.ParticipationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

}
