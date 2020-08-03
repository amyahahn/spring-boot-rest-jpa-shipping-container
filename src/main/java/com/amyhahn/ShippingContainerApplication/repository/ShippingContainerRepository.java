package com.amyhahn.ShippingContainerApplication.repository;

import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.amyhahn.ShippingContainerApplication.model.ShippingContainer;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public interface ShippingContainerRepository extends JpaRepository<ShippingContainer, Long>{

    @Query("SELECT sc from ShippingContainer sc " +
            "WHERE sc.status = :status " +
            "AND sc.containerOwnerId = :containerOwnerId")
    List<ShippingContainer> findAvailableContainersByOwnerId(@Param("containerOwnerId") Long containerOwnerId, @Param("status") String status);

}
