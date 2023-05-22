package recipes.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.dto.UserDto;
import recipes.exception.UserAlreadyExistsException;
import recipes.model.User;
import recipes.model.UserDetailsImpl;
import recipes.repository.UserRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByEmail(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Not found: " + email);
        }

        return new UserDetailsImpl(user.get());
    }

    public void register(UserDto user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        user.setRole("REGISTERED");
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(modelMapper.map(user, User.class));
    }
}
