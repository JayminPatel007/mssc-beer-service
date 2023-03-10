package dev.jaymin.msscbeerservice.web.controller;

import dev.jaymin.msscbeerservice.services.BeerService;
import dev.jaymin.msscbeerservice.web.model.BeerDto;
import dev.jaymin.msscbeerservice.web.model.BeerPageList;
import dev.jaymin.msscbeerservice.web.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beer")
@RequiredArgsConstructor
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;
    private final BeerService beerService;

    @GetMapping
    public ResponseEntity<BeerPageList> listBeers(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "beerName", required = false) String beerName,
            @RequestParam(value = "beerStyle", required = false) BeerStyle beerStyle,
            @RequestParam(value = "showInventoryOnHand", defaultValue = "false") boolean showInventoryOnHand
    ) {
        if (pageNumber == null || pageNumber == 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPageList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> handleGetBeerById(
            @PathVariable("beerId") UUID beerId,
            @RequestParam(value = "showInventoryOnHand", defaultValue = "false") boolean showInventoryOnHand) {
        return new ResponseEntity<>(beerService.getById(beerId, showInventoryOnHand), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity handleCreateBeer(@RequestBody @Validated BeerDto beerDto) {
        BeerDto savedBeer = beerService.createNewBeer(beerDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", savedBeer.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleUpdateBeer(@RequestBody @Validated BeerDto beerDto, @PathVariable("beerId") UUID beerID) {
        beerService.updateBeerById(beerID, beerDto);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleDeleteBeer(@PathVariable("beerId") UUID beerId) {
        beerService.deleteBeerById(beerId);
    }
}
