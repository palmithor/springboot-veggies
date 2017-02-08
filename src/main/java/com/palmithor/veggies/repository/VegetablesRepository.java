package com.palmithor.veggies.repository;

import com.palmithor.veggies.entity.VegetableEntity;
import com.palmithor.veggies.model.Vegetable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.stream.Stream;

/**
 * An crud repository for Vegetables
 * <p>
 * See {@link CrudRepository}
 *
 * @author palmithor
 * @since 8.2.2017.
 */
public interface VegetablesRepository extends Repository<VegetableEntity, Long> {


    Stream<VegetableEntity> findAll();

    VegetableEntity findOneById(final Long id);

    VegetableEntity save(final VegetableEntity entity);

    long count();

    void delete(final Long id);
}
