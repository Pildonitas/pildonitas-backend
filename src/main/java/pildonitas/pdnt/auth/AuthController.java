package pildonitas.pdnt.auth;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pildonitas.pdnt.security.CustomUserDetail;
import pildonitas.pdnt.security.jwt.JwtService;
import pildonitas.pdnt.user.UserService;
import pildonitas.pdnt.user.dto.UserRequest;
import pildonitas.pdnt.user.dto.UserResponse;

import static pildonitas.pdnt.user.Role.USER;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) {
        UserRequest userRequestWithRoleByDefault = new UserRequest(userRequest.username(), userRequest.name(), userRequest.email(), userRequest.password(), userRequest.allergies());
        UserResponse userResponse = userService.addUser(userRequestWithRoleByDefault);
        return new ResponseEntity<>(userResponse,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetail);

        JwtResponse jwtResponse = new JwtResponse(token);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
}