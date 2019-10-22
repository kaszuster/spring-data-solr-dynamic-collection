package de.dev86.app;

import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends SolrCrudRepository<Order, Long> {
}
