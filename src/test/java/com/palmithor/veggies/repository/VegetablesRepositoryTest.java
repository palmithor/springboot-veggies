package com.palmithor.veggies.repository;

import com.palmithor.veggies.entity.VegetableEntity;
import com.palmithor.veggies.model.Vegetable;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

/**
 * @author palmithor
 * @since 8.2.2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class VegetablesRepositoryTest {

    @Autowired
    private VegetablesRepository repository;

    @Autowired
    private Flyway flyway;

    /**
     * To make sure all tests have the same data when running
     */
    @Before
    public void setUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    public void testFindAll() {
        List<VegetableEntity> all = repository.findAll().map(vegetableEntity -> vegetableEntity).collect(Collectors.toList());
        assertThat(all, hasSize(3));
    }

    @Test
    public void testFindByIdSuccess() {
        VegetableEntity entity = repository.findOneById(1L);
        assertThat(entity.getId(), is(1L));
        assertThat(entity.getName(), is("Cucumber"));
        assertThat(entity.getPrice(), is(new BigDecimal(12)));
        assertThat(entity.getVersion(), is(notNullValue()));
        assertThat(entity.getCreated(), is(notNullValue()));
        assertThat(entity.getUpdated(), is(notNullValue()));
    }

    @Test
    public void testFindByIdNotFound() {
        VegetableEntity entity = repository.findOneById(-1L);
        assertThat(entity, is(nullValue()));
    }

    @Test
    public void testCreateNew() {
        VegetableEntity entity = new VegetableEntity("Sweet Potato", BigDecimal.TEN);
        VegetableEntity save = repository.save(entity);
        assertThat(save.getName(), is(entity.getName()));
        assertThat(save.getPrice(), is(entity.getPrice()));
        assertThat(save.getId(), is(notNullValue()));
        assertThat(save.getCreated(), is(notNullValue()));
        assertThat(save.getUpdated(), is(notNullValue()));
        assertThat(save.getVersion(), is(notNullValue()));
    }

    @Test
    public void testUpdate() {
        VegetableEntity entity = repository.findOneById(1L);
        BigDecimal oldPrice = entity.getPrice();
        Date oldUpdated = entity.getUpdated();
        BigDecimal newPrice = oldPrice.add(BigDecimal.ONE);
        entity.setPrice(newPrice);
        VegetableEntity save = repository.save(entity);
        assertThat(save.getName(), is(entity.getName()));
        assertThat(save.getPrice().doubleValue(), is(greaterThan(oldPrice.doubleValue())));
        assertThat(save.getId(), is(entity.getId()));
        assertThat(save.getCreated(), is(entity.getCreated()));
        assertThat(save.getVersion(), is(notNullValue()));
        // TODO find out why @PreUpdate is not being called assertThat(save.getUpdated(), is(greaterThan(oldUpdated)));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testDeleteNotFound() {
        repository.delete(-1L);
    }

    @Test
    public void testDelete() {
        repository.delete(1L);
        assertThat(repository.findOneById(1L), is(nullValue()));
    }

    @Test
    public void testCount() {
        assertThat(repository.count(), is(3L));
    }
}