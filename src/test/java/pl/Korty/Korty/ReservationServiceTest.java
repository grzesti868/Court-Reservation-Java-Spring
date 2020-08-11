package pl.Korty.Korty;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.Korty.Korty.model.entities.Squash_CourtsEntity;
import pl.Korty.Korty.model.enums.SexEnum;
import pl.Korty.Korty.model.enums.StatusEnum;
import pl.Korty.Korty.model.repositories.AddressRepository;
import pl.Korty.Korty.model.repositories.ReservationRepository;
import pl.Korty.Korty.model.repositories.Squash_CourtsRepository;
import pl.Korty.Korty.model.repositories.UserRepository;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.responses.ReservationRestModel;
import pl.Korty.Korty.model.responses.Squash_CourtRestModel;
import pl.Korty.Korty.model.responses.UserRestModel;
import pl.Korty.Korty.model.services.ReservationService;
import pl.Korty.Korty.model.services.Squash_CourtService;
import pl.Korty.Korty.model.services.UserService;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("h2")
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private Squash_CourtsRepository squash_courtsRepository;

    @Autowired
    private Squash_CourtService squash_courtService;


    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
    }

    @Test
    @Transactional
    void addReservation_addNewReservationWithNewUser_addReservationSuccessfully() throws ParseException {
        final AddressRestModel courtAddress = new AddressRestModel("COURTnameStreet",1,2,"COURTnameCity","44-100","nameCountry");
        final Squash_CourtRestModel court = new Squash_CourtRestModel(courtAddress,5);
        Long courtId = squash_courtService.add(court);
        assertEquals(1,squash_courtsRepository.count());

        final AddressRestModel userAddress = new AddressRestModel("nameStreet",1,2,"nameCity","44-100","nameCountry");
        final UserRestModel user = new UserRestModel("gregvader","admin123","gregvader@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, userAddress,null);

        String stringStartDate = "2020-09-20 10:30:00";
        String stringEndDate = "2020-09-20 12:30:00";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = format.parse(stringStartDate);
        Date endDate = format.parse(stringEndDate);
        final ReservationRestModel reservation = new ReservationRestModel(startDate,endDate,2,"Bede z psem",courtId,null,user);

        reservationService.add(reservation);
        assertEquals(1,reservationRepository.count());
        assertEquals(2,addressRepository.count());
        assertEquals(1,userRepository.count());
    }

    @Test
    @Transactional
    void addReservation_addNewReservationWithOldUser_addReservationSuccessfully() throws ParseException {
        final AddressRestModel courtAddress = new AddressRestModel("COURTnameStreet",1,2,"COURTnameCity","44-100","nameCountry");
        final Squash_CourtRestModel court = new Squash_CourtRestModel(courtAddress,5);
        Long courtId = squash_courtService.add(court);
        assertEquals(1,squash_courtsRepository.count());

        final AddressRestModel userAddress = new AddressRestModel("nameStreet",1,2,"nameCity","44-100","nameCountry");
        final UserRestModel user = new UserRestModel("gregvader","admin123","gregvader@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, userAddress,null);

        userService.add(user);
        assertEquals(1,userRepository.count());


        String stringStartDate = "2020-09-20 10:30:00";
        String stringEndDate = "2020-09-20 12:30:00";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = format.parse(stringStartDate);
        Date endDate = format.parse(stringEndDate);
        final ReservationRestModel reservation = new ReservationRestModel(startDate,endDate,2,"Bede z psem",courtId,user.getLogin(),null);

        reservationService.add(reservation);
        assertEquals(1,reservationRepository.count());
        assertEquals(2,addressRepository.count());
        assertEquals(1,userRepository.count());
    }

    @Test
    @Transactional
    void deleteReservation_deleteReservationButDontUserAndCourt_successfulDelete() throws ParseException {
        final AddressRestModel courtAddress = new AddressRestModel("COURTnameStreet",1,2,"COURTnameCity","44-100","nameCountry");
        final Squash_CourtRestModel court = new Squash_CourtRestModel(courtAddress,5);
        Long courtId = squash_courtService.add(court);
        assertEquals(1,squash_courtsRepository.count());

        final AddressRestModel userAddress = new AddressRestModel("nameStreet",1,2,"nameCity","44-100","nameCountry");
        final UserRestModel user = new UserRestModel("gregvader","admin123","gregvader@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, userAddress,null);

        userService.add(user);
        assertEquals(1,userRepository.count());


        String stringStartDate = "2020-09-20 10:30:00";
        String stringEndDate = "2020-09-20 12:30:00";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = format.parse(stringStartDate);
        Date endDate = format.parse(stringEndDate);
        final ReservationRestModel reservation = new ReservationRestModel(startDate,endDate,2,"Bede z psem",courtId,user.getLogin(),null);

        Long reservationId = reservationService.add(reservation);
        assertEquals(1,reservationRepository.count());
        assertEquals(2,addressRepository.count());
        assertEquals(1,userRepository.count());

        reservationService.deleteByID(reservationId);
        assertEquals(0,reservationRepository.count());
        assertEquals(2,addressRepository.count());
        assertEquals(1,userRepository.count());
    }

    @Test
    @Transactional
    void updateReservation_updatePureReservationDetails_successfulUpdate() throws ParseException {
        final AddressRestModel courtAddress = new AddressRestModel("COURTnameStreet",1,2,"COURTnameCity","44-100","nameCountry");
        final Squash_CourtRestModel court = new Squash_CourtRestModel(courtAddress,5);
        Long courtId = squash_courtService.add(court);
        assertEquals(1,squash_courtsRepository.count());

        final AddressRestModel userAddress = new AddressRestModel("nameStreet",1,2,"nameCity","44-100","nameCountry");
        final UserRestModel user = new UserRestModel("gregvader","admin123","gregvader@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, userAddress,null);

        userService.add(user);
        assertEquals(1,userRepository.count());


        String stringStartDate = "2020-09-20 10:30:00";
        String stringEndDate = "2020-09-20 12:30:00";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = format.parse(stringStartDate);
        Date endDate = format.parse(stringEndDate);
        final ReservationRestModel reservation = new ReservationRestModel(startDate,endDate,2,"Bede z psem",courtId,user.getLogin(),null);

        Long reservationId = reservationService.add(reservation);
        assertEquals(1,reservationRepository.count());
        assertEquals(2,addressRepository.count());
        assertEquals(1,userRepository.count());

        final ReservationRestModel updateReservation = new ReservationRestModel(startDate,endDate,2,"Bede z psem I KOTEM",courtId,user.getLogin(),null);

        reservationService.update(reservationId,updateReservation);
        assertEquals(updateReservation,reservationService.getById(reservationId));
    }
}