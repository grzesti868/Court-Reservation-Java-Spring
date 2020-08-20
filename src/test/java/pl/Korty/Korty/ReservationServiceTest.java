package pl.Korty.Korty;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
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
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


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
        userRepository.deleteAll();
        squash_courtsRepository.deleteAll();
    }

    @MockBean
    private ReservationRestModel reservationRestModelMock;

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

        reservationService.deleteById(reservationId);
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

    @Test
    void findAllUserReservation_findReservationsByInvalidCourtId_returnNull() {
        assertNull(reservationService.getAllByCourtId(1L));
    }

    @Test
    void findAllCourtReservation_findCourtsByInvalidUsersId_returnNull() {
        assertNull(reservationService.getAllByUserLogin("greeg"));
    }

    @Test
    void updateReservation_updateReservationByInvalidId_returnNull() {
        assertNull(reservationService.update(2L,reservationRestModelMock));
    }

    @Test
    void deleteUser_deleteUserByInvalidId_returnFalse() {
        assertEquals(false,reservationService.deleteById(1L));
    }

    @Test
    void getReservationById_getReservationByInvalidId_returnNull() {
        assertNull(reservationService.getById(2L));
    }

    @Test
    void addReservation_addReservationWithInvalidCourtId_refuseToAdd() throws ParseException {
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
        final ReservationRestModel reservation = new ReservationRestModel(startDate,endDate,2,"Bede z psem",courtId+1,user.getLogin(),null);

        Long reservationId = reservationService.add(reservation);
        assertEquals(-2L,reservationId);

    }

    @Test
    void addReservation_addReservationWithEmptyUser_refuseToAdd() throws ParseException {
        final AddressRestModel courtAddress = new AddressRestModel("COURTnameStreet",1,2,"COURTnameCity","44-100","nameCountry");
        final Squash_CourtRestModel court = new Squash_CourtRestModel(courtAddress,5);
        Long courtId = squash_courtService.add(court);
        assertEquals(1,squash_courtsRepository.count());



        String stringStartDate = "2020-09-20 10:30:00";
        String stringEndDate = "2020-09-20 12:30:00";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = format.parse(stringStartDate);
        Date endDate = format.parse(stringEndDate);
        final ReservationRestModel reservation = new ReservationRestModel(startDate,endDate,2,"Bede z psem",courtId, null ,null);

        Long reservationId = reservationService.add(reservation);
        assertEquals(-3L,reservationId);

    }

    @Test
    void addReservation_addReservationWithEmptyUserAddress_refuseToAdd() throws ParseException {
        final AddressRestModel courtAddress = new AddressRestModel("COURTnameStreet",1,2,"COURTnameCity","44-100","nameCountry");
        final Squash_CourtRestModel court = new Squash_CourtRestModel(courtAddress,5);
        Long courtId = squash_courtService.add(court);
        assertEquals(1,squash_courtsRepository.count());

        final UserRestModel user = new UserRestModel("gregvader","admin123","gregvader@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, null,null);



        String stringStartDate = "2020-09-20 10:30:00";
        String stringEndDate = "2020-09-20 12:30:00";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = format.parse(stringStartDate);
        Date endDate = format.parse(stringEndDate);
        final ReservationRestModel reservation = new ReservationRestModel(startDate,endDate,2,"Bede z psem",courtId,user.getLogin(),null);

        Long reservationId = reservationService.add(reservation);
        assertEquals(-4L,reservationId);
        assertEquals(0,userRepository.count());
        assertEquals(0,reservationRepository.count());


    }

    /*@Test
    @Transactional
    void testOfDateValid() throws ParseException {
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

        String stringStartDate1 = "2020-09-20 12:30:00";
        String stringEndDate1 = "2020-09-20 13:30:00";
        Date startDate1 = format.parse(stringStartDate1);
        Date endDate1 = format.parse(stringEndDate1);
        assertEquals(Boolean.TRUE,reservationService.isTimeValid(startDate1,endDate1,courtId));
    }*/


    @Test
    @Transactional
    void addAnotherReservation_addReservationOnNewUserAndThenAddReservationOnOldUser_successfulAdd() throws ParseException {
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

        String stringStartDate1 = "2020-10-20 10:30:00";
        String stringEndDate1 = "2020-10-20 12:30:00";
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate1 = format.parse(stringStartDate);
        Date endDate1 = format.parse(stringEndDate);
        final ReservationRestModel reservation1 = new ReservationRestModel(startDate,endDate,2,"Bede z psem",courtId,user.getLogin(),null);
        reservationService.add(reservation1);

        assertEquals(2, reservationRepository.count());

    }
}
