package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.repository.StatsRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class StatsService {
    private final StatsRepository statsRepository;

}
