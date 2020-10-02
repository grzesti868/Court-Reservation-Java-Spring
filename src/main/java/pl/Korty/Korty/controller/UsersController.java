package pl.Korty.Korty.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.Korty.Korty.model.responses.UserRestModel;
import pl.Korty.Korty.services.UserService;

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserRestModel>> listAllUsers() {
        final List<UserRestModel> userList = userService.getAll();

        return ResponseEntity.ok(userList);
    }

    @GetMapping("{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #username == authentication.name")
    public ResponseEntity<UserRestModel> getUserByName(@PathVariable final String username) {

        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addUser(@RequestBody final UserRestModel user) {
        return ResponseEntity.ok("User has been added, Id: " + userService.add(user));
    }

    @PutMapping("{username}")
    @PreAuthorize("hasAuthority('address:write') or #username == authentication.name")
    public ResponseEntity<UserRestModel> updateUserByUsername(@PathVariable final String username,@RequestBody final UserRestModel user){
        return ResponseEntity.ok(userService.update(username,user));
    }

    @DeleteMapping("{username}")
    @PreAuthorize("hasAuthority('address:write') or #username == authentication.name")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable final String username){
        userService.deleteByUsername(username);
        return ResponseEntity.ok("User has been deleted.");
    }
}
