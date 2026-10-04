package in.arup.mini_library.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
    @GetMapping("/api/profile")
    public String profile(Authentication authentication){
        return "Hello " + authentication.getName() + ", this is your profile.";
    }
}
