package dev.jaymin.msscbeerservice.services;

import dev.jaymin.msscbeerservice.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
    BeerDto getById(UUID beerId);

    BeerDto createNewBeer(BeerDto beerDto);

    BeerDto updateBeerById(UUID beerID, BeerDto beerDto);

    void deleteBeerById(UUID beerId);
}
