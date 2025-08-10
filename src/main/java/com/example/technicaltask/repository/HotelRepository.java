package com.example.technicaltask.repository;

import com.example.technicaltask.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HotelRepository extends JpaRepository<Long, Hotel>, JpaSpecificationExecutor<Hotel> {

    @Query("SELECT h.brand AS key, COUNT(h) AS count FROM Hotel h GROUP BY h.brand")
    List<Map<String, Object>> countGroupByBrand();

    @Query("SELECT h.address.city AS key, COUNT(h) AS count FROM Hotel h GROUP BY h.address.city")
    List<Map<String, Object>> countGroupByCity();

    @Query("SELECT h.address.country AS key, COUNT(h) AS count FROM Hotel h GROUP BY h.address.country")
    List<Map<String, Object>> countGroupByCountry();

    @Query("SELECT a AS key, COUNT(h) AS count FROM Hotel h JOIN h.amenities a GROUP BY a")
    List<Map<String, Object>> countGroupByAmenity();

}
