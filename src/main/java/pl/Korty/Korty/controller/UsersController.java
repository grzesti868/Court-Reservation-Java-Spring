package pl.Korty.Korty.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.Korty.Korty.model.responses.UserRestModel;
import pl.Korty.Korty.model.services.UserService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("users")
public class UsersController { //todo: make test

    private final UserService userService;

    public UsersController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserRestModel>> listAllUsers() {
        final List<UserRestModel> userList = userService.getAll();

        return ResponseEntity.ok(userList);
    }

    @GetMapping("{login}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #login == authentication.name")
    public ResponseEntity<UserRestModel> getUserByName(@PathVariable final String login) {

        return ResponseEntity.ok(userService.getByLogin(login));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addUser(@RequestBody final UserRestModel user) {
        return ResponseEntity.ok("User has been added, Id: " + userService.add(user));
    }

    @PutMapping("{login}")
    @PreAuthorize("hasAuthority('address:write') or #login == authentication.name")
    public ResponseEntity<UserRestModel> updateUserByLogin(@PathVariable final String login,@RequestBody final UserRestModel user){
        return ResponseEntity.ok(userService.update(login,user));
    }

    @DeleteMapping("{login}")
    @PreAuthorize("hasAuthority('address:write') or #login == authentication.name")
    public ResponseEntity<String> deleteUserByLogin(@PathVariable final String login){
        userService.deleteByLogin(login);
        return ResponseEntity.ok("User has been deleted.");
    }
}
