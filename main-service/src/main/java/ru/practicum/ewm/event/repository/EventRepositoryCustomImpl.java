package ru.practicum.ewm.event.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventSortTypes;
import ru.practicum.ewm.event.EventStates;

import java.time.LocalDateTime;
import java.util.*;

public class EventRepositoryCustomImpl implements EventRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Event> getEventsByFilters(String text, List<Long> categoryIds, Boolean paid, LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortTypes sortType,
                                          int from, int size) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);
        Root<Event> event = cq.from(Event.class);

        List<Predicate> conditions = new ArrayList<>();

        conditions.add(cb.equal(event.get("state"), EventStates.PUBLISHED));
        if (text != null && !text.isEmpty()) {
            String searchPattern = "%" + text + "%";
            conditions.add(cb.or(cb.like(cb.lower(event.get("title")), searchPattern),
                                 cb.like(cb.lower(event.get("annotation")), searchPattern),
                                 cb.like(cb.lower(event.get("description")), searchPattern)));
        }
        if (categoryIds != null && !categoryIds.isEmpty()) {
            conditions.add(cb.in(event.get("category").get("id")).value(categoryIds));
        }
        if (paid != null ) {
            conditions.add(cb.equal(event.get("paid"), paid));
        }
        if (rangeStart == null && rangeEnd == null) {
            conditions.add(cb.greaterThanOrEqualTo(event.get("eventDate"), LocalDateTime.now()));
        } else {
            if (rangeStart != null) {
                conditions.add(cb.greaterThanOrEqualTo(event.get("eventDate"), rangeStart));
            }
            if (rangeEnd != null) {
                conditions.add(cb.lessThanOrEqualTo(event.get("eventDate"), rangeEnd));
            }
        }
        if (onlyAvailable != null && onlyAvailable) {
            conditions.add(cb.lt(event.get("confirmedRequests"), event.get("participantLimit")));
        }
        cq.select(event);
        cq.where(conditions.toArray(new Predicate[0]));

        switch (sortType) {
            case EVENT_DATE -> cq.orderBy(cb.asc(event.get("eventDate")));
            case VIEWS -> cq.orderBy(cb.desc(event.get("views")));
            default -> cq.orderBy(cb.asc(event.get("id")));
        }

        return entityManager.createQuery(cq)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();


/*
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
*/

    }

    @Override
    public List<Event> getAdminEventsByFilters(List<Long> users, List<EventStates> states, List<Long> categories,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);
        Root<Event> event = cq.from(Event.class);

        List<Predicate> conditions = new LinkedList<>();

        if (users != null && !users.isEmpty()) {
            conditions.add(cb.in(event.get("initiator").get("id")).value(users));
        }
        if (states != null && !states.isEmpty()) {
            conditions.add(cb.in(event.get("state")).value(states));
        }
        if (categories != null && !categories.isEmpty()) {
            conditions.add(cb.in(event.get("category").get("id")).value(categories));
        }
        if (rangeStart != null) {
            conditions.add(cb.greaterThanOrEqualTo(event.get("eventDate"), rangeStart));
        }
        if (rangeEnd != null) {
            conditions.add(cb.lessThanOrEqualTo(event.get("eventDate"), rangeEnd));
        }

        cq.select(event);
        cq.where(conditions.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(event.get("id")));

        return entityManager.createQuery(cq)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();
    }



    //    public List<Event> getEventsByFiltersV2(String text, List<Long> categoryIds, Boolean paid, LocalDateTime rangeStart,
//                                          LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortTypes sortType,
//                                          int from, int size) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//
//    }

}
