package ru.practicum.ewm.event.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventSortTypes;
import ru.practicum.ewm.event.EventStates;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventRepositoryCustomImpl implements EventRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Event> getEventsByFilters(String text, List<Long> categoryIds, Boolean paid, LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortTypes sortType,
                                          int from, int size) {
        Map<String, Object> queryParameters = new HashMap<>();

        StringBuilder query = new StringBuilder("SELECT * from events e ");
        query.append(" where e.state = :state ");
        queryParameters.put("state", EventStates.PUBLISHED);
        if (text != null && !text.isBlank()) {
            query.append("""
                     and (e.title like lower(concat('%', :text,'%'))
                     or e.annotation like lower(concat('%', :text,'%'))
                     or e.description like lower(concat('%', :text,'%')))
                    """);
            queryParameters.put("text", text);
        }
        if (categoryIds != null && !categoryIds.isEmpty()) {
            query.append("""
                     and (e.category_id in :categoryIds)
                    """);
            queryParameters.put("categoryIds", categoryIds);
        }
        if (rangeStart != null) {
            query.append(" and (e.event_date >= :rangeStart) ");
            queryParameters.put("rangeStart", rangeStart);
        }
        if (rangeEnd != null) {
            query.append(" and (e.event_date <= :rangeEnd) ");
            queryParameters.put("rangeEnd", rangeEnd);
        }
        if (onlyAvailable != null && onlyAvailable.equals(true)) {
            query.append(" and (e.event_date <= :rangeEnd) ");
            queryParameters.put("rangeEnd", rangeEnd);
        }
        switch (sortType) {
            case VIEWS:
                query.append(" order by e.views DESC ");
                break;
            default:
                query.append(" order by e.event_date ASC ");
        }

        Query nativeQuery =  entityManager.createNativeQuery(query.toString(), Event.class);
        for (Map.Entry<String, Object> entry : queryParameters.entrySet()) {
            nativeQuery.setParameter(entry.getKey(), entry.getValue());
        }
        return nativeQuery.getResultList();
    }

    @Override
    public List<Event> getAdminEventsByFilters(List<Long> users, List<EventStates> states, List<Long> categories,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);
        Root<Event> event = cq.from(Event.class);

        List<Predicate> conditions = new LinkedList<>();
        if (users != null && !users.isEmpty()) {

            Path<Object> iid = event.get("initiator");
            Path<Object> initiator = event.get("initiator");
            Path<Object> id = initiator.get("id");
            conditions.add(cb.in(event.get("initiator").get("id")).value(users));
        }

        cq.select(event);
        cq.where(conditions.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(event.get("id")));


        TypedQuery<Event> q = entityManager.createQuery(cq);
        List<Event> events = q.getResultList();

        return events;
    }

    //    public List<Event> getEventsByFiltersV2(String text, List<Long> categoryIds, Boolean paid, LocalDateTime rangeStart,
//                                          LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortTypes sortType,
//                                          int from, int size) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//
//    }

}
