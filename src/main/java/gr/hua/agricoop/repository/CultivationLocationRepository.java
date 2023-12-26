package gr.hua.agricoop.repository;

import gr.hua.agricoop.entity.CultivationLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CultivationLocationRepository extends JpaRepository<CultivationLocation, Integer> {
}
