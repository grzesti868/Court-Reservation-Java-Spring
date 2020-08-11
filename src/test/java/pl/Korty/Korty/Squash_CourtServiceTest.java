package pl.Korty.Korty;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.Korty.Korty.model.repositories.AddressRepository;
import pl.Korty.Korty.model.repositories.Squash_CourtsRepository;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.responses.Squash_CourtRestModel;
import pl.Korty.Korty.model.services.Squash_CourtService;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("h2")
public class Squash_CourtServiceTest {

    @Autowired
    private Squash_CourtService squash_courtService;

    @Autowired
    private Squash_CourtsRepository squash_courtsRepository;

    @Autowired
    private AddressRepository addressRepository;

    @AfterEach
    void tearDown() {
        squash_courtsRepository.deleteAll();
    }

    @Test
    void addCourt_addNewCourt_newCourtInDb() {
        final AddressRestModel courtAddress = new AddressRestModel("COURTnameStreet",1,2,"COURTnameCity","44-100","nameCountry");
        final Squash_CourtRestModel court = new Squash_CourtRestModel(courtAddress,5);
        squash_courtService.add(court);
        assertEquals(1,squash_courtsRepository.count());
    }

    @Test
    void deleteCourt_deleteCourtById_courtDeleted() {
        final AddressRestModel courtAddress = new AddressRestModel("COURTnameStreet",1,2,"COURTnameCity","44-100","nameCountry");
        final Squash_CourtRestModel court = new Squash_CourtRestModel(courtAddress,5);
        Long courtId = squash_courtService.add(court);
        assertEquals(1,squash_courtsRepository.count());
        squash_courtService.deleteByID(courtId);
        assertEquals(0,squash_courtsRepository.count());
    }

    @Test
    @Transactional
    void updateCourt_updateCourt_successfulUpdate() {
        final AddressRestModel courtAddress = new AddressRestModel("COURTnameStreet",1,2,"COURTnameCity","44-100","nameCountry");
        final Squash_CourtRestModel court = new Squash_CourtRestModel(courtAddress,5);
        Long courtId = squash_courtService.add(court);
        assertEquals(1,squash_courtsRepository.count());
        final Squash_CourtRestModel updateCourt = new Squash_CourtRestModel(courtAddress,5000);
        squash_courtService.update(courtId,updateCourt);
        assertEquals(updateCourt,squash_courtService.getById(courtId));
    }
}
