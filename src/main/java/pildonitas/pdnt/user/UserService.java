package pildonitas.pdnt.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pildonitas.pdnt.exceptions.EntityAlreadyExistsException;
import pildonitas.pdnt.exceptions.EntityNotFoundException;
import pildonitas.pdnt.security.CustomUserDetail;
import pildonitas.pdnt.user.dto.UserMapper;
import pildonitas.pdnt.user.dto.UserRequest;
import pildonitas.pdnt.user.dto.UserResponse;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws EntityNotFoundException {
        return userRepository.findByUsername(username)
                .map(CustomUserDetail::new)
                .orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), "username", username));
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::entityToDto).toList();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), "Id", id.toString()));
    }

    public UserResponse getUserResponseById(Long id) {
        User user = getUserById(id);
        return UserMapper.entityToDto(user);
    }

    public UserResponse addUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.username())) {
            throw new EntityAlreadyExistsException(User.class.getSimpleName(), "username", userRequest.username());
        }
        User newUser = UserMapper.dtoToEntity(userRequest);
        newUser.setPassword(passwordEncoder.encode(userRequest.password()));
        User savedUser = userRepository.save(newUser);
        return UserMapper.entityToDto(savedUser);
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User updateUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), "id", id.toString()));
        if(!updateUser.getUsername().equals(userRequest.username())) {
            if (userRepository.existsByUsername(userRequest.username())) {
                throw new EntityAlreadyExistsException(User.class.getSimpleName(), "username", userRequest.username());
            }
            updateUser.setUsername(userRequest.username());
        }
        updateUser.setName(userRequest.name());
        updateUser.setEmail(userRequest.email());
        updateUser.setPassword(passwordEncoder.encode(userRequest.password()));
        updateUser.setAllergies(userRequest.allergies());
        User newUser = userRepository.save(updateUser);
        return UserMapper.entityToDto(newUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(User.class.getSimpleName(), "id", id.toString());
        }
        getUserById(id);
        userRepository.deleteById(id);
    }
}