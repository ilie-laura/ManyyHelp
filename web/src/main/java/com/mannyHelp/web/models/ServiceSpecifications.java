package com.mannyHelp.web.models;

import com.mannyHelp.web.models.Service; // sau Service/ServiceModel depinde cum se numește entitatea ta
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ServiceSpecifications {

    public static Specification<Service> withFilters(String keyword, String location, String category) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (keyword != null && !keyword.trim().isEmpty()) {
                String searchPattern = "%" + keyword.trim().toLowerCase() + "%";
                Predicate nameMatch = cb.like(cb.lower(root.get("numeServiciu")), searchPattern);

                predicates.add(nameMatch);
            }


            if (location != null && !location.trim().isEmpty()) {
                String locPattern = "%" + location.trim().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("locatie")), locPattern));
            }


            if (category != null && !category.trim().isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("categorie")), category.trim().toLowerCase()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}