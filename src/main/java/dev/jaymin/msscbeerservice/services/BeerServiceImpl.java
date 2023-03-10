package dev.jaymin.msscbeerservice.services;

import dev.jaymin.msscbeerservice.domain.Beer;
import dev.jaymin.msscbeerservice.repositories.BeerRepository;
import dev.jaymin.msscbeerservice.web.exceptions.NotFoundException;
import dev.jaymin.msscbeerservice.web.mappers.BeerMapper;
import dev.jaymin.msscbeerservice.web.model.BeerDto;
import dev.jaymin.msscbeerservice.web.model.BeerPageList;
import dev.jaymin.msscbeerservice.web.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper mapper;
    @Override
    @Cacheable(cacheNames = "beerCache", key = "#beerId" ,condition = "#showInventoryOnHand == false")
    public BeerDto getById(UUID beerId, boolean showInventoryOnHand) {
        Beer beer = beerRepository.findById(beerId)
                .orElseThrow(NotFoundException::new);
        return showInventoryOnHand ? mapper.beerToBeerDtoEnhancedWithInventory(beer)
                    : mapper.beerToBeerDto(beer);
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

    @Override
    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
    public BeerPageList listBeers(String beerName, BeerStyle beerStyle, PageRequest pageRequest, boolean showInventoryOnHand) {
        BeerPageList beerPageList;
        Page<Beer> beerPage;

        if (StringUtils.hasLength(beerName) && beerStyle != null) {
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle.name(), pageRequest);
        } else if (StringUtils.hasLength(beerName) && beerStyle == null) {
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (!StringUtils.hasLength(beerName) && beerStyle != null) {
            beerPage = beerRepository.findALlByBeerStyle(beerStyle.name(), pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        beerPageList = new BeerPageList(beerPage
                .getContent()
                .stream()
                .map(beer -> showInventoryOnHand ?
                        mapper.beerToBeerDtoEnhancedWithInventory(beer) : mapper.beerToBeerDto(beer))
                .toList(),
                PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
                beerPage.getTotalElements());

        return beerPageList;

    }
}
