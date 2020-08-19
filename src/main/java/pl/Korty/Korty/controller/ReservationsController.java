package pl.Korty.Korty.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ReservationRestModel>> listAllReservations(){
        final List<ReservationRestModel> reservationsList = reservationService.getAll();

        return ResponseEntity.ok(reservationsList);
    }

    @GetMapping("byUser/{userId}")
    @PreAuthorize("hasAuthority('reservation:read')") //todo: guest can only by his id
    public ResponseEntity<List<ReservationRestModel>> listAllReservationsByUserId(@PathVariable final Long userId){
        Optional<List<ReservationRestModel>> reservationList = Optional.ofNullable(reservationService.getAllByUserId(userId));
        return reservationList.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("byCourt/{courtId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ReservationRestModel>> getAllReservationByCourtId(@PathVariable final Long courtId){
       Optional<List<ReservationRestModel>> reservationList = Optional.ofNullable(reservationService.getAllByCourtId(courtId));
        return reservationList.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('reservation:read')") //todo: guest can only by reservationId which he posses
    public ResponseEntity<ReservationRestModel> getReservationById(@PathVariable final Long id){
        Optional<ReservationRestModel> reservation = Optional.ofNullable(reservationService.getById(id));
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
    @PreAuthorize("hasAuthority('reservation:write')") //todo: guest can only by reservationId which he posses
    public ResponseEntity<ReservationRestModel> updateReservationById(@PathVariable final Long id,@RequestBody final ReservationRestModel reservation){
        Optional<ReservationRestModel> reservationRestModel = Optional.ofNullable(reservationService.update(id,reservation));
        return reservationRestModel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('reservation:write')") //todo: guest can only by reservationId which he posses
    public ResponseEntity<String> deleteReservationById(@PathVariable final Long id){
        Boolean isDeleted =  reservationService.deleteByID(id);
        if(isDeleted)
            return ResponseEntity.status(HttpStatus.OK).body("Reservation has been deleted.");
        else
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid reservation's id.");
    }
}
