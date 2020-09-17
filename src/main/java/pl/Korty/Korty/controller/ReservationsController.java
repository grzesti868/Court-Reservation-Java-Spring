package pl.Korty.Korty.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.Korty.Korty.model.responses.ReservationRestModel;
import pl.Korty.Korty.services.ReservationService;

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ReservationRestModel>> listAllReservations(){
        final List<ReservationRestModel> reservationsList = reservationService.getAll();

        return ResponseEntity.ok(reservationsList);
    }

    @GetMapping("byUser/{username}")
    @PreAuthorize("hasAuthority('reservation:read') or #username == authentication.name")
    public ResponseEntity<List<ReservationRestModel>> listAllReservationsByUserUsername(@PathVariable final String username){
        return ResponseEntity.ok(reservationService.getAllByUserUsername(username));
    }


    @GetMapping("byCourt/{courtId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ReservationRestModel>> getAllReservationByCourtId(@PathVariable final Long courtId){
        return ResponseEntity.ok(reservationService.getAllByCourtId(courtId));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('reservation:read')")
    public ResponseEntity<ReservationRestModel> getReservationById(@PathVariable final Long id){
        return ResponseEntity.ok(reservationService.getById(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addReservation(@RequestBody final ReservationRestModel reservation) {
            return ResponseEntity.ok("Reservation has been added, Id: "+reservationService.add(reservation));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('reservation:write')")
    public ResponseEntity<ReservationRestModel> updateReservationById(@PathVariable final Long id,@RequestBody final ReservationRestModel reservation){
        return ResponseEntity.ok(reservationService.update(id,reservation));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('reservation:write')")
    public ResponseEntity<String> deleteReservationById(@PathVariable final Long id){
        reservationService.deleteById(id);
        return ResponseEntity.ok("Reservations has been deleted");
    }

}
