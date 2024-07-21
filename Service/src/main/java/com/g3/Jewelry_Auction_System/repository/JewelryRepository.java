package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.Jewelry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JewelryRepository extends JpaRepository<Jewelry, Integer> {
    Optional<Jewelry> findByJewelryId(int jewelryId);

    @Query(value = "Select * from jewelry where jewelry_name like '%'+:name+'%'", nativeQuery = true)
    List<Jewelry> getJewelriesByName(@Param("name") String name);

    @Override
    Page<Jewelry> findAll(Pageable pageable);

    @Query(value = "Select * FROM jewelry where jewelry_category_id = :categoryId", nativeQuery = true)
    List<Jewelry> getByCategory(int categoryId);
    @Query(value = "SELECT DISTINCT j.* FROM Jewelry j LEFT JOIN Auction a ON j.jewelry_id = a.jewelry_id WHERE (a.status = 'Ended' OR a.jewelry_id IS NULL) AND a.winner_id IS NULL AND j.status = 1", nativeQuery = true)
    List<Jewelry> getJewelriesForAuction();
    @Query(value = "SELECT DISTINCT j.* FROM Jewelry j LEFT JOIN Auction a ON j.jewelry_id = a.jewelry_id WHERE (a.status = 'Pending' OR a.status = 'Ongoing') AND a.winner_id IS NULL AND j.status = 1", nativeQuery = true)
    List<Jewelry> getJewelriesOnAuction();

}
