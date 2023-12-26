package gr.hua.agricoop.repository;

import gr.hua.agricoop.entity.Cooperative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CooperativeRepository extends JpaRepository<Cooperative, Integer> {
}
