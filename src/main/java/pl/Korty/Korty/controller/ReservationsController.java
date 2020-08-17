package pl.Korty.Korty.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.Korty.Korty.model.responses.ReservationRestModel;
import pl.Korty.Korty.model.services.ReservationService;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("reservations")
public class ReservationsController {


    private final ReservationService reservationService;

    public ReservationsController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @GetMapping
    public ResponseEntity<List<ReservationRestModel>> listAllReservations(){
        final List<ReservationRestModel> reservationsList = reservationService.getAll();

        return ResponseEntity.ok(reservationsList);
    }

    @GetMapping("byUser/{userId}")
    public ResponseEntity<List<ReservationRestModel>> listAllReservationsByUserId(@PathVariable final Long userId){
        Optional<List<ReservationRestModel>> reservationList = Optional.ofNullable(reservationService.getAllByUserId(userId));
        if(reservationList.isPresent())
            return ResponseEntity.ok(reservationList.get());
        else
            return ResponseEntity.notFound().build();
    }


    @GetMapping("byCourt/{courtId}")
    public ResponseEntity<List<ReservationRestModel>> getAllReservationByCourtId(@PathVariable final Long courtId){
       Optional<List<ReservationRestModel>> reservationList = Optional.ofNullable(reservationService.getAllByCourtId(courtId));
       if(reservationList.isPresent())
            return ResponseEntity.ok(reservationList.get());
       else
           return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservationRestModel> getReservationById(@PathVariable final Long id){
        Optional<ReservationRestModel> reservation = Optional.ofNullable(reservationService.getById(id));
        if(reservation.isPresent())
            return ResponseEntity.ok(reservation.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addReservation(@RequestBody final ReservationRestModel reservation) {
        Long reservationsId = reservationService.add(reservation);
        if(reservationsId > 0)
            return ResponseEntity.ok("Reservation has been added, Id: "+reservationsId);
        else if(reservationsId.equals(-1L))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Reservation is empty");
        else if(reservationsId.equals(-2L))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empty id court.");
        else if(reservationsId.equals(-3L))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User was not specified");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong reservation details (wrong user's info or period of time).");
    }

    @PutMapping("{id}")
    public ResponseEntity<ReservationRestModel> updateReservationById(@PathVariable final Long id,@RequestBody final ReservationRestModel reservation){
        Optional<ReservationRestModel> reservationRestModel = Optional.ofNullable(reservationService.update(id,reservation));
        if(reservationRestModel.isPresent())
             return ResponseEntity.ok(reservationRestModel.get());
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteReservationById(@PathVariable final Long id){
        Boolean isDeleted =  reservationService.deleteByID(id);
        if(isDeleted)
            return ResponseEntity.status(HttpStatus.OK).body("Reservation has been deleted.");
        else
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid reservation's id.");
    }
}
