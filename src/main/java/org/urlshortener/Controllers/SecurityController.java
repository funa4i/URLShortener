//package org.urlshortener.Controllers;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
////import org.springframework.security.authentication.AuthenticationManager;
////import org.springframework.security.authentication.BadCredentialsException;
////import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
////import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.urlshortener.Advice.ExceptionView;
//import org.urlshortener.Dto.JwtResponse;
//import org.urlshortener.Dto.UserValid;
//import org.urlshortener.Excemptions.ObjectAlreadyExists;
//import org.urlshortener.services.JwtServ;
//import org.urlshortener.services.UrlShortenerServ;
//import org.urlshortener.services.UserServ;
//
//import java.time.LocalDateTime;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(value = "/auth")
//public class SecurityController {
//
//    private final UserServ userServ;
//    private final JwtServ jwtServ;
//    private final AuthenticationManager authenticationManager;
//
//    @PostMapping("/signUp")
//    public ResponseEntity<?> signUp(@RequestBody UserValid user){
//        try {
//            userServ.createNewUser(user);
//            return null;
//        }
//        catch (ObjectAlreadyExists ex){
//            return new ResponseEntity<ExceptionView>(
//                    new ExceptionView(400, ex.getMessage()),
//                            HttpStatus.valueOf(400)
//            );
//        }
//    }
//
//    @PostMapping("/logIn")
//    public ResponseEntity<?> logIn(@RequestBody UserValid user){
//        var u = userServ.reformuUser(user);
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword()));
//        }
//        catch (BadCredentialsException e){
//            return new ResponseEntity<ExceptionView>(
//                    new ExceptionView(HttpStatus.UNAUTHORIZED.value(), "Неверный логин или пароль"),
//                            HttpStatus.UNAUTHORIZED);
//        }
//        UserDetails userDetails = userServ.loadUserByUsername(user.getEmail());
//        String token = jwtServ.generateToken(userDetails);
//        return  ResponseEntity.ok(new JwtResponse(token));
//    }
//}
