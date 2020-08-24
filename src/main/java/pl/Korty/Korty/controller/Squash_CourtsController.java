package pl.Korty.Korty.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.Korty.Korty.model.responses.Squash_CourtRestModel;
import pl.Korty.Korty.model.services.Squash_CourtService;

import java.util.List;

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

    @GetMapping("byAddress/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Squash_CourtRestModel> getByAddressId(@PathVariable final Long id){
        return ResponseEntity.ok(squash_courtService.getByAddressId(id));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Squash_CourtRestModel> getById(@PathVariable final Long id){
        return ResponseEntity.ok(squash_courtService.getById(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addCourt(@RequestBody final Squash_CourtRestModel court) {
        return ResponseEntity.ok("Court added successful. Id: "+squash_courtService.add(court));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Squash_CourtRestModel> updateCourtById(@PathVariable final Long id,@RequestBody final Squash_CourtRestModel court){
        return ResponseEntity.ok(squash_courtService.update(id,court));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteCourtById(@PathVariable final Long id){

        squash_courtService.deleteByID(id);
        return ResponseEntity.ok("Squash court has been deleted.");

    }
}
