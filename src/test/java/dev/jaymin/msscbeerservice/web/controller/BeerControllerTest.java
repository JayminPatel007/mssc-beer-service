package dev.jaymin.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jaymin.msscbeerservice.web.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void handleGetBeerById() throws Exception {
        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void handleCreateBeer() throws Exception {
        BeerDto beerDto = BeerDto.builder().build();
        String beerToJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(
                post("/api/v1/beer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerToJson)
                )
                .andExpect(status().isCreated());
    }

    @Test
    void handleUpdateBeer() throws Exception {
        BeerDto beerDto = BeerDto.builder().build();
        String beerToJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(
                        put("/api/v1/beer/" + UUID.randomUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(beerToJson)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void handleDeleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/" + UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }
}