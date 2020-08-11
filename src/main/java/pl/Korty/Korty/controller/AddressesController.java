package pl.Korty.Korty.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.Korty.Korty.model.repositories.AddressRepository;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.responses.UserRestModel;
import pl.Korty.Korty.model.services.AddressService;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;

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
        Optional<AddressRestModel> address = Optional.ofNullable(addressService.getById(id));
        if(address.isPresent())
        return ResponseEntity.ok(addressService.getById(id));
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addAddress(@RequestBody final AddressRestModel address) {
        Long id = addressService.add(address);
        if(id>0)
        return ResponseEntity.ok("Address has been added, ID: " + id);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PutMapping("{id}")
    public ResponseEntity<AddressRestModel> updateAddress(@PathVariable final Long id,@RequestBody AddressRestModel address){
        Optional<AddressRestModel> updatedAddress = Optional.ofNullable(addressService.update(id,address));
        if(updatedAddress.isPresent())
        return ResponseEntity.ok(updatedAddress.get());
        else
            return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable final Long id){
       Boolean ifDeleted =  addressService.deleteByID(id);
       if(ifDeleted)
        return ResponseEntity.ok("Address has been deleted.");
       else
           return new ResponseEntity<>("Invalid address id.",HttpStatus.NOT_FOUND);
    }

}
