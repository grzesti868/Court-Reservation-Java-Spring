package pl.Korty.Korty.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.services.AddressService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("addresses")
public class AddressesController {

    private final AddressService addressService;

    public AddressesController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AddressRestModel>> listAllAddresses(){
        final List<AddressRestModel> addressesList = addressService.getAll();

        return ResponseEntity.ok(addressesList);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AddressRestModel> getAddressById(@PathVariable final Long id){

        return ResponseEntity.ok(addressService.getById(id));

    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addAddress(@RequestBody final AddressRestModel address) {
        return ResponseEntity.ok("Address has been added, ID: " + addressService.add(address));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AddressRestModel> updateAddress(@PathVariable final Long id,@RequestBody AddressRestModel address){
        return ResponseEntity.ok(addressService.update(id,address));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteAddress(@PathVariable final Long id){
        addressService.deleteByID(id);
        return ResponseEntity.ok("Address has been deleted.");
    }

}
