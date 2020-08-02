package pl.Korty.Korty.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.Korty.Korty.model.repositories.AddressRepository;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.responses.UserRestModel;
import pl.Korty.Korty.model.services.AddressService;

import javax.persistence.Id;
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
    public ResponseEntity<List<AddressRestModel>> listAllAddresses(){
        final List<AddressRestModel> addressesList = addressService.getAll();

        return ResponseEntity.ok(addressesList);
    }

    @GetMapping("{id}")
    public ResponseEntity<AddressRestModel> getAddressById(@PathVariable final Long id){
        return ResponseEntity.ok(addressService.getById(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addAddress(@RequestBody final AddressRestModel address) {
        return ResponseEntity.ok("Address has been updated, ID: " + addressService.add(address));
    }

    @PutMapping("{id}")
    public ResponseEntity<AddressRestModel> updateAddress(@PathVariable final Long id,@RequestBody AddressRestModel address){
        return ResponseEntity.ok(addressService.update(id,address));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAddress(@RequestParam final Long id){
        addressService.deleteByID(id);
        return ResponseEntity.ok("Address has been deleted.");
    }

}
