package pl.Korty.Korty.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.Korty.Korty.model.entities.UsersEntity;
import pl.Korty.Korty.model.responses.UserRestModel;
import pl.Korty.Korty.model.services.UserService;

import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("users")
public class UsersController {

    private final UserService userService;

    public UsersController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserRestModel>> listAllUsers() {
        final List<UserRestModel> userList = userService.getAll();

        return ResponseEntity.ok(userList);
    }

    @GetMapping("{login}")
    public ResponseEntity<UserRestModel> getUserByName(@PathVariable final String login) {
        return ResponseEntity.ok(userService.getByLogin(login));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> addUser(@RequestBody final UserRestModel user) {
        return ResponseEntity.ok(userService.add(user));
    }

    @PutMapping("{id}")
    public ResponseEntity<UserRestModel> updateUserById(@PathVariable final Long id,@RequestBody final UserRestModel user){
        return ResponseEntity.ok(userService.update(id,user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable final Long id){
        userService.deleteByID(id);
        return new ResponseEntity<>("User has been deleted.", HttpStatus.OK);
    }
}
