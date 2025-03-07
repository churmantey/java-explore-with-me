package ru.practicum.ewm.event;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.user.User;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @Size(max = 120)
    @NotBlank
    @Column(name = "title")
    private String title;

    @Size(max = 2000)
    @NotBlank
    @Column(name = "annotation")
    private String annotation;

    @Size(max = 7000)
    @NotBlank
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(name = "location_lat")
    private Double locationLat;

    @Column(name = "location_lon")
    private Double locationLon;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "participant_limit")
    private Integer participantLimit;

    @NotNull
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    private Boolean requestModeration = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private EventStates state;

    @PositiveOrZero
    @Column(name = "confirmed_requests")
    private long confirmedRequests;

    @PositiveOrZero
    @Column(name = "views")
    private long views;

    public boolean hasFreeSpots() {
        return this.getParticipantLimit() == null || this.getParticipantLimit().equals(0)
                || this.getConfirmedRequests() < this.getParticipantLimit();

    }

    public void addConfirm() {
        this.confirmedRequests++;
    }

    public void removeConfirm() {
        this.confirmedRequests--;
    }

    public void addView() {
        this.views++;
    }

    public boolean isPublished() {
        return (this.getState() != null && this.getState().equals(EventStates.PUBLISHED));
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer()
                .getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer()
                .getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;

        Event event = (Event) o;
        return getId() != null && Objects.equals(getId(), event.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer()
                .getPersistentClass()
                .hashCode()
                : getClass().hashCode();
    }
}
