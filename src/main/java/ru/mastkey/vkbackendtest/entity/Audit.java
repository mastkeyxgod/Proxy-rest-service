package ru.mastkey.vkbackendtest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "audit")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "method")
    private String method;

    @Size(max = 255)
    @Column(name = "endpoint")
    private String endpoint;

    @Size(max = 255)
    @Column(name = "status")
    private String status;

    @Size(max = 255)
    @Column(name = "username")
    private String username;

    @Size(max = 255)
    @Column(name = "ip")
    private String ip;

    @Column(name = "\"timestamp\"")
    private LocalDateTime timestamp;

}