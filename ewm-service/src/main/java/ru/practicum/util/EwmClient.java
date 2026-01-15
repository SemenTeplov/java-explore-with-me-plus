package main.java.ru.practicum.util;

import main.java.ru.practicum.constant.Values;
import main.java.ru.practicum.dto.HitEventDTO;

import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

public class EwmClient {
    public static void send(String uri) {
        RestClient client = RestClient.create();

        client.post().uri(Values.ADDRESS_STATS_SERVER)
                .body(new HitEventDTO(Values.APPLICATION, uri, Values.EWM_IP, LocalDateTime.now()))
                .retrieve()
                .toBodilessEntity();
    }
}
