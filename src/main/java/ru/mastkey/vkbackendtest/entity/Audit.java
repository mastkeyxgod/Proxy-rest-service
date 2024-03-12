package ru.mastkey.vkbackendtest.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;

    private String endpoint;

    private String status;

    private String user;

    private String ip;

    private LocalDateTime timestamp;

    public Audit(LocalDateTime timestamp, String user, String endpoint, String status) {
        this.timestamp = timestamp;
        this.user = user;
        this.endpoint = endpoint;
        this.status = status;
    }
}
