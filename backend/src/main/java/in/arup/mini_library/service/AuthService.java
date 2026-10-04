package in.arup.mini_library.service;

import in.arup.mini_library.dto.LoginRequest;
import in.arup.mini_library.dto.LoginResponse;
import in.arup.mini_library.dto.RegisterRequest;
import in.arup.mini_library.entity.User;
import in.arup.mini_library.repository.UserRepository;
import in.arup.mini_library.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequest request){
        //check if email already exists
        if(userRepository.findByEmail(request.email()).isPresent()){
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("USER");
        userRepository.save(user);
        return "User registered successfully";
    }

    public LoginResponse login(LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = userRepository.findByEmail(request.email()).orElseThrow();
        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }
}
