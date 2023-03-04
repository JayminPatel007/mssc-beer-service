package dev.jaymin.msscbeerservice.bootstarp;

import dev.jaymin.msscbeerservice.domain.Beer;
import dev.jaymin.msscbeerservice.repositories.BeerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@AllArgsConstructor
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
        log.debug("This is called");
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        log.info("load BeerObjects has called");
        if (beerRepository.count() == 0) {
            beerRepository.save(Beer.builder()
                    .beerName("Mango Bobs").beerStyle("IPA")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(3333701000000001L)
                    .price(new BigDecimal("12.95")).build());

            beerRepository.save(Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(3333701000000002L)
                    .price(new BigDecimal("11.95")).build());

            log.info("Loaded Beers: {}", beerRepository.count());

        }
    }
}
