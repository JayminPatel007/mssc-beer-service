package dev.jaymin.msscbeerservice.web.mappers;

import dev.jaymin.msscbeerservice.domain.Beer;
import dev.jaymin.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}
