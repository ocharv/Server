package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        try {
            this.service.createUser(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED); // 201
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409
        }
    }
    @PostMapping("/login")
    public ResponseEntity<User> authenticateUser(@RequestBody User user) {
        try {
            User authUser = this.service.authenticateUser(user);
            if (authUser != null) {
                return new ResponseEntity<>(authUser, HttpStatus.OK); // 200
            }else{
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); //404
            }
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); //404
        }
    }
    // We return all the users
    @GetMapping("/users")
    Iterable<User> all() {
        return service.getUsers();
    }
    // We can find a user based on his/her Id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user=this.service.getUserById(id);
        if (user != null){
            return new ResponseEntity<>(user, HttpStatus.OK); //200
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404
        }
    }
    @PutMapping("/users/{id}")
    //ResponseEntity<String>???
    public ResponseEntity<User> updateUser(@RequestBody User user){
        try {
            this.service.updateUser(user);
            return new ResponseEntity<>(user, HttpStatus.NO_CONTENT); //204
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404
        }
    }
}
