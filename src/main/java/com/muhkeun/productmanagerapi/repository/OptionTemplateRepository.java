package com.muhkeun.productmanagerapi.repository;

import com.muhkeun.productmanagerapi.model.entity.OptionTemplate;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionTemplateRepository extends JpaRepository<OptionTemplate, Long> {

    @EntityGraph(attributePaths = "values")
    List<OptionTemplate> findAll();

}
