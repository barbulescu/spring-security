package com.barbulescu.springsecurity.db;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, OtpRepository otpRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
    }


    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean check(Otp otpToValidate) {
        return otpRepository.findOtpByUsername(otpToValidate.getUsername())
                .map(Otp::getCode)
                .filter(code -> Objects.equals(otpToValidate.getCode(), code))
                .isPresent();
    }

    public void auth(User user) {
        userRepository.findUserByUsername(user.getUsername())
                .filter(u -> passwordEncoder.matches(user.getPassword(), u.getPassword()))
                .map(this::renewOtp)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials."));
    }

    private Otp renewOtp(User u) {
        String code = GenerateCodeUtil.generateCode();
        Optional<Otp> userOtp = otpRepository.findOtpByUsername(u.getUsername());

        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            otp.setCode(code);
            return otp;
        } else {
            Otp otp = new Otp();
            otp.setUsername(u.getUsername());
            otp.setCode(code);
            return otpRepository.save(otp);
        }


    }
}