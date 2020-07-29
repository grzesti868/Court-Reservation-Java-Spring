package pl.Korty.Korty.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.responses.ReservationRestModel;
import pl.Korty.Korty.model.services.ReservationService;

import java.util.List;

@Controller
@RequestMapping(name = "reservations")
public class ReservationsController {


    private final ReservationService reservationService;

    public ReservationsController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @GetMapping
    public ResponseEntity<List<ReservationRestModel>> listAllReservations(){
        final List<ReservationRestModel> resevationsList = reservationService.getAll();

        return ResponseEntity.ok(resevationsList);
    }

    @GetMapping("byUser/{userId}")
    public ResponseEntity<List<ReservationRestModel>> listAllReservationsByUserId(@PathVariable final Long userId){
        return ResponseEntity.ok(reservationService.getAllByUserId(userId));
    }


    /*@GetMapping("{id}")
    public ResponseEntity<ReservationRestModel> getReservationById(@PathVariable final Long id){
        return ResponseEntity.ok(reservationService.getById(id));
    }*/


}
