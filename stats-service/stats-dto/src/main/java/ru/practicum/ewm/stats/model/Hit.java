package ru.practicum.ewm.stats.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "hits")
public class Hit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "app")
    private String app;

    @NotBlank
    @Column(name = "uri")
    private String uri;

    @NotBlank
    @Pattern(regexp = "(\\d{1,3}\\.){3}\\d{1,3}", message = "Incorrect ip address")
    @Column(name = "ip")
    private String ip;

    @Column(name = "created")
    private LocalDateTime timestamp;

}
