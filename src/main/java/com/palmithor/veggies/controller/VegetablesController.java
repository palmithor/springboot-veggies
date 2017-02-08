package com.palmithor.veggies.controller;

import com.palmithor.veggies.controller.exception.ResourceNotFoundException;
import com.palmithor.veggies.entity.VegetableEntity;
import com.palmithor.veggies.model.DeleteResult;
import com.palmithor.veggies.model.Vegetable;
import com.palmithor.veggies.model.VegetableRequest;
import com.palmithor.veggies.repository.VegetablesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Simple REST controller for vegetables
 *
 * @author palmithor
 * @since 8.2.2017.
 */
@RestController
@RequestMapping(value = "/api/v1/vegetables")
@Validated
public class VegetablesController {

    @Autowired VegetablesRepository vegetablesRepository;

    @RequestMapping(method = RequestMethod.GET, value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Vegetable> getAll() {
        return vegetablesRepository
                .findAll()
                .map(Vegetable::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST, value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Vegetable create(@Valid @RequestBody final VegetableRequest request) {
        return new Vegetable(
                vegetablesRepository.save(new VegetableEntity(request.getName(), new BigDecimal(request.getPrice())))
        );
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Vegetable getById(@PathVariable @Digits(fraction = 0, integer = Integer.MAX_VALUE, message = "id should be a number") final String id) {
        VegetableEntity entity = vegetablesRepository.findOneById(Long.valueOf(id));
        if (entity == null) {
            throw new ResourceNotFoundException();
        }
        return new Vegetable(entity);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Vegetable update(@PathVariable @Digits(fraction = 0, integer = Integer.MAX_VALUE, message = "id should be a number") final String id, @RequestBody final VegetableRequest request) {
        VegetableEntity entity = vegetablesRepository.findOneById(Long.valueOf(id));
        if (entity == null) {
            throw new ResourceNotFoundException();
        }
        entity.setPrice(new BigDecimal(request.getPrice()));
        entity.setName(request.getName());
        return new Vegetable(vegetablesRepository.save(entity));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeleteResult delete(@PathVariable @Digits(fraction = 0, integer = Integer.MAX_VALUE, message = "id should be a number") final String id) {
        Long entityId = Long.valueOf(id);
        VegetableEntity entity = vegetablesRepository.findOneById(entityId);
        if (entity == null) {
            throw new ResourceNotFoundException();
        }
        vegetablesRepository.delete(entityId);
        return new DeleteResult(entityId);
    }
}
