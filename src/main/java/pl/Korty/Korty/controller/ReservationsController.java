package pl.Korty.Korty.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.Korty.Korty.model.entities.ReservationsEntity;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.responses.ReservationRestModel;
import pl.Korty.Korty.model.responses.UserRestModel;
import pl.Korty.Korty.model.services.ReservationService;

import java.util.List;

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
        return ResponseEntity.ok(reservationService.getAllByUserId(userId));
    }


    @GetMapping("byCourt/{courtId}")
    public ResponseEntity<List<ReservationRestModel>> getAllReservationByCourtId(@PathVariable final Long courtId){
        return ResponseEntity.ok(reservationService.getAllByCourtId(courtId));
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservationRestModel> getReservationById(@PathVariable final Long id){
        return ResponseEntity.ok(reservationService.getById(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> addReservation(@RequestBody final ReservationRestModel reservation) {
        System.out.println(reservation.toString());
        return ResponseEntity.ok(reservationService.add(reservation));
    }

    @PutMapping("{id}")
    public ResponseEntity<ReservationRestModel> updateReservationById(@PathVariable final Long id,@RequestBody final ReservationRestModel reservation){
        return ResponseEntity.ok(reservationService.update(id,reservation));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteReservationById(@PathVariable final Long id){
        reservationService.deleteByID(id);
        return new ResponseEntity<>("Reservation has been deleted.", HttpStatus.OK);
    }

}
