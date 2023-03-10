package dev.jaymin.msscbeerservice.services;

import dev.jaymin.msscbeerservice.web.model.BeerDto;
import dev.jaymin.msscbeerservice.web.model.BeerPageList;
import dev.jaymin.msscbeerservice.web.model.BeerStyle;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
    BeerDto getById(UUID beerId);

    BeerDto createNewBeer(BeerDto beerDto);

    BeerDto updateBeerById(UUID beerID, BeerDto beerDto);

    void deleteBeerById(UUID beerId);

    BeerPageList listBeers(String beerName, BeerStyle beerStyle, PageRequest pageRequest);
}
