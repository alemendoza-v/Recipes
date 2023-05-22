package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.dto.UserDto;
import recipes.exception.UserAlreadyExistsException;
import recipes.model.User;
import recipes.service.UserDetailsServiceImpl;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
@Validated
public class AuthController {
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @PostMapping
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto user) {
        userDetailsService.register(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintValidationException() {
        return new ResponseEntity<>("Error in data", HttpStatus.BAD_REQUEST);
    }
}
