package dev.jaymin.msscbeerservice.services;

import dev.jaymin.msscbeerservice.domain.Beer;
import dev.jaymin.msscbeerservice.repositories.BeerRepository;
import dev.jaymin.msscbeerservice.web.exceptions.NotFoundException;
import dev.jaymin.msscbeerservice.web.mappers.BeerMapper;
import dev.jaymin.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper mapper;
    @Override
    public BeerDto getById(UUID beerId) {
        return mapper.beerToBeerDto(
                beerRepository.findById(beerId)
                        .orElseThrow(NotFoundException::new)
        );
    }

    @Override
    public BeerDto createNewBeer(BeerDto beerDto) {
        Beer beerToSave = mapper.beerDtoToBeer(beerDto);
        return mapper.beerToBeerDto(
                beerRepository.save(beerToSave)
        );
    }

    @Override
    public BeerDto updateBeerById(UUID beerID, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerID).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return mapper.beerToBeerDto(beerRepository.save(beer));
    }

    @Override
    public void deleteBeerById(UUID beerId) {
        beerRepository.deleteById(beerId);
    }
}
