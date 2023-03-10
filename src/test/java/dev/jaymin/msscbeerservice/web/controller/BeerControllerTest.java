package dev.jaymin.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jaymin.msscbeerservice.bootstarp.BeerLoader;
import dev.jaymin.msscbeerservice.services.BeerService;
import dev.jaymin.msscbeerservice.web.model.BeerDto;
import dev.jaymin.msscbeerservice.web.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @Test
    void handleGetBeerById() throws Exception {
        given(beerService.getById(any(), any())).willReturn(getValidBeerCreatedDto());
        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void handleCreateBeer() throws Exception {
        given(beerService.createNewBeer(any())).willReturn(getValidBeerCreatedDto());
        BeerDto beerDto = getValidBeerDto();
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
        given(beerService.updateBeerById(any(),any())).willReturn(getValidBeerCreatedDto());
        BeerDto beerDto = getValidBeerDto();
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

    private BeerDto getValidBeerDto() {
        return BeerDto.builder()
                .beerName("New Beer")
                .beerStyle(BeerStyle.ALE)
                .price(new BigDecimal("2.99"))
                .upc(BeerLoader.BEER_1_UPC)
                .build();
    }

    private BeerDto getValidBeerCreatedDto() {
        return BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("New Beer")
                .beerStyle(BeerStyle.ALE)
                .price(new BigDecimal("2.99"))
                .upc(BeerLoader.BEER_1_UPC)
                .build();
    }
}