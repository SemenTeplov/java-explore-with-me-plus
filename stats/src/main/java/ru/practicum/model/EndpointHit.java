package ru.practicum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "hits")
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "app", nullable = false)
    String app;

    @Column(name = "uri", nullable = false, length = 500)
    String uri;

    @Column(name = "ip", nullable = false, length = 45)
    String ip;

    @Column(name = "hit_time", nullable = false)
    LocalDateTime timestamp;
}
