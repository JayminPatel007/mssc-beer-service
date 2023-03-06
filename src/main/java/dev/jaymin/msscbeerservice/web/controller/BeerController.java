package dev.jaymin.msscbeerservice.web.controller;

import dev.jaymin.msscbeerservice.web.model.BeerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {


    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> handleGetBeerById(@PathVariable("beerId") UUID beerId) {

        //todo impl
        return new ResponseEntity<>(BeerDto.builder().build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity handleCreateBeer(@RequestBody @Validated BeerDto beerDto) {

        // todo impl
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleUpdateBeer(@RequestBody @Validated BeerDto beerDto, @PathVariable("beerId") UUID beerID) {
        // todo impl
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleDeleteBeer(@PathVariable("beerId") UUID beerId) {
        // todo impl
    }
}
