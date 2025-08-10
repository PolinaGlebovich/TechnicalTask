package com.example.technicaltask.repository.specification;

import com.example.technicaltask.entity.Hotel;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class HotelSpecification {

    public static Specification<Hotel> hasName(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Hotel> hasBrand(String brand) {
        return (root, query, cb) ->
                brand == null ? null : cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%");
    }

    public static Specification<Hotel> hasCity(String city) {
        return (root, query, cb) ->
                city == null ? null : cb.equal(cb.lower(root.get("address").get("city")), city.toLowerCase());
    }

    public static Specification<Hotel> hasCountry(String country) {
        return (root, query, cb) ->
                country == null ? null : cb.equal(cb.lower(root.get("address").get("country")), country.toLowerCase());
    }

    public static Specification<Hotel> hasAmenity(String amenity) {
        return (root, query, cb) -> {
            if (amenity == null) return null;
            Join<Hotel, String> amenities = root.joinSet("amenities", JoinType.INNER);
            return cb.equal(cb.lower(amenities), amenity.toLowerCase());
        };
    }
}
