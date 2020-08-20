package pl.Korty.Korty.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.Korty.Korty.model.responses.UserRestModel;
import pl.Korty.Korty.model.services.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("{login}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #login == authentication.name")
    public ResponseEntity<UserRestModel> getUserByName(@PathVariable final String login) {
        Optional<UserRestModel> user = Optional.ofNullable(userService.getByLogin(login));
        if(user.isPresent())
        return ResponseEntity.ok(userService.getByLogin(login));
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addUser(@RequestBody final UserRestModel user) {
        Long id = userService.add(user);
        if(id>0)
            return ResponseEntity.ok("User has been added, ID: " + id);
        else if(id.equals(-1L))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad user's data (user's address is empty).");
        else if(id.equals(-2L))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User's login already exists.");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User's data is empty");
    }

    @PutMapping("{login}")
    @PreAuthorize("hasAuthority('address:write') or #login == authentication.name")
    public ResponseEntity<UserRestModel> updateUserByLogin(@PathVariable final String login,@RequestBody final UserRestModel user){
        Optional<UserRestModel> updatedUser = Optional.ofNullable(userService.update(login,user));
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    @DeleteMapping("{login}")
    @Transactional
    @PreAuthorize("hasAuthority('address:write') or #login == authentication.name")
    public ResponseEntity<String> deleteUserByLogin(@PathVariable final String login){

       Boolean isDeleted =  userService.deleteByLogin(login);
       if(isDeleted)
        return ResponseEntity.status(HttpStatus.OK).body("User has been deleted.");
       else
           return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid user's id.");
    }
}
