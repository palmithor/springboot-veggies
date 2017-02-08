package com.palmithor.veggies.controller;

import com.palmithor.veggies.App;
import com.palmithor.veggies.entity.VegetableEntity;
import com.palmithor.veggies.model.VegetableRequest;
import com.palmithor.veggies.repository.VegetablesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.palmithor.veggies.TestUtils.getDefaultGson;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for the VegetablesController
 *
 * @author palmithor
 * @since 8.2.2017.
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = App.class)
public class VegetablesControllerTest {

    @Autowired private WebApplicationContext wac;

    @MockBean private VegetablesRepository vegetablesRepository;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testFindAllSuccessful() throws Exception {
        given(vegetablesRepository.findAll()).willReturn(Arrays.stream(new VegetableEntity[]{dummyEntity()}));

        mockMvc.perform(get("/api/v1/vegetables/")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("[{\"name\":\"name\",\"price\":10}]"));
    }

    @Test
    public void testFindById() throws Exception {
        given(vegetablesRepository.findOneById(1L)).willReturn(dummyEntity());

        mockMvc.perform(get("/api/v1/vegetables/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("{\"name\":\"name\",\"price\":10}"));
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        given(vegetablesRepository.findOneById(1L)).willReturn(null);

        mockMvc.perform(get("/api/v1/vegetables/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"message\":\"Resource not found\"}"));
    }

    @Test
    public void testFindByIdInvalidId() throws Exception {

        mockMvc.perform(get("/api/v1/vegetables/abc")
                .content(getDefaultGson().toJson(dummyRequest()))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"message\":\"id should be a number\"}"));
    }

    @Test
    public void testUpdate() throws Exception {
        given(vegetablesRepository.findOneById(1L)).willReturn(dummyEntity());
        VegetableRequest request = dummyRequest();
        given(vegetablesRepository.save(any())).willReturn(new VegetableEntity(request.getName(), BigDecimal.ONE));

        mockMvc.perform(put("/api/v1/vegetables/1")
                .content(getDefaultGson().toJson(request))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("{\"name\":\"newName\",\"price\":1}"));
    }

    @Test
    public void testUpdateWithNoBody() throws Exception {
        mockMvc.perform(put("/api/v1/vegetables/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"message\":\"Required request body is missing\"}"));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        given(vegetablesRepository.findOneById(1L)).willReturn(null);

        mockMvc.perform(put("/api/v1/vegetables/1")
                .content(getDefaultGson().toJson(dummyRequest()))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"message\":\"Resource not found\"}"));
    }

    @Test
    public void testDelete() throws Exception {
        given(vegetablesRepository.findOneById(1L)).willReturn(dummyEntity());
        doNothing().when(vegetablesRepository).delete(anyLong());

        mockMvc.perform(delete("/api/v1/vegetables/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("{\"deleted_id\":1}"));
    }


    @Test
    public void testDeleteNotFound() throws Exception {
        given(vegetablesRepository.findOneById(1L)).willReturn(null);
        doNothing().when(vegetablesRepository).delete(anyLong());

        mockMvc.perform(delete("/api/v1/vegetables/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"message\":\"Resource not found\"}"));
    }

    @Test
    public void testCreate() throws Exception {
        given(vegetablesRepository.save(any())).willReturn(dummyEntity());

        mockMvc.perform(post("/api/v1/vegetables/")
                .content(getDefaultGson().toJson(dummyRequest()))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("{\"name\":\"name\",\"price\":10}"));
    }

    @Test
    public void testCreateNameMissing() throws Exception {
        given(vegetablesRepository.save(any())).willReturn(dummyEntity());

        mockMvc.perform(post("/api/v1/vegetables/")
                .content(getDefaultGson().toJson(new VegetableRequest(null, 2.0)))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"message\":\"name may not be null\"}"));
    }

    @Test
    public void testCreatePriceMissing() throws Exception {
        given(vegetablesRepository.save(any())).willReturn(dummyEntity());

        mockMvc.perform(post("/api/v1/vegetables/")
                .content(getDefaultGson().toJson(new VegetableRequest("name", null)))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"message\":\"price may not be null\"}"));
    }

    private VegetableEntity dummyEntity() {
        return new VegetableEntity("name", BigDecimal.TEN);
    }


    private VegetableRequest dummyRequest() {
        return new VegetableRequest("newName", BigDecimal.ONE.doubleValue());
    }
}