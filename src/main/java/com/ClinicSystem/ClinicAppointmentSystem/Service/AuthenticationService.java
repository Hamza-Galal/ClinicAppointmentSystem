package com.ClinicSystem.ClinicAppointmentSystem.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.LoginRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.RegisterRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.LoginResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.InvalidCredintialsException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.UserAlreadyExistsException;
import com.ClinicSystem.ClinicAppointmentSystem.Model.User;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.Role;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.UserRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Security.JwtService;

@Service 
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    public AuthenticationService(UserRepository userRepository, JwtService jwtService,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }
    public  void register(RegisterRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email Already Exists");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.PATIENT);
        userRepository.save(user);
    }
    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()
        -> new InvalidCredintialsException("Invalid Email or Password."));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredintialsException("Invalid Email or Password.");
        }
        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return new LoginResponse(token, user.getEmail(), user.getRole());
    }
}
