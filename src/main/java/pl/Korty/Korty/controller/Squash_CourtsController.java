package pl.Korty.Korty.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.Korty.Korty.model.responses.Squash_CourtRestModel;
import pl.Korty.Korty.model.services.Squash_CourtService;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("courts")
public class Squash_CourtsController {

    final private Squash_CourtService squash_courtService;

    public Squash_CourtsController(Squash_CourtService squash_courtService) {
        this.squash_courtService = squash_courtService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Squash_CourtRestModel>> listAllCourts() {
        final List<Squash_CourtRestModel> courtList = squash_courtService.getAll();

        return ResponseEntity.ok(courtList);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Squash_CourtRestModel> getById(@PathVariable final Long id){
        Optional<Squash_CourtRestModel> court = Optional.ofNullable(squash_courtService.getById(id));
        return court.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addCourt(@RequestBody final Squash_CourtRestModel court) {
        Long courtId = squash_courtService.add(court);
        if(courtId>0)
            return ResponseEntity.ok("Court has been added: "+courtId);
        else if(courtId.equals(-1L))
            return  ResponseEntity.badRequest().body("New court is empty.");
        else
            return  ResponseEntity.badRequest().body("New court has no address.");
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Squash_CourtRestModel> updateCourtById(@PathVariable final Long id,@RequestBody final Squash_CourtRestModel court){
        Optional<Squash_CourtRestModel> updatedCourt = Optional.ofNullable(squash_courtService.update(id,court));
        return updatedCourt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteCourtById(@PathVariable final Long id){
        Boolean isDeleted = squash_courtService.deleteByID(id);
        if(isDeleted)
        return new ResponseEntity<>("Squash court has been deleted.", HttpStatus.OK);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Court not found.");
    }
}
