package pildonitas.pdnt.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import pildonitas.pdnt.exceptions.EntityAlreadyExistsException;
import pildonitas.pdnt.user.dto.UserRequest;
import pildonitas.pdnt.user.dto.UserResponse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;
    private User user;
    private UserResponse userResponse;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("TestUser");
        user.setName("Test Name");
        user.setEmail("test@email.com");
        user.setPassword("$2a$12$C8CFsIqDYxhkJloBvWXetOGN..TN7gD1V.VK152fNZ9gPp5IGRXZ2");
        user.setAllergies("Penicilina");

        userResponse = new UserResponse(1L, "TestUser", "Test Name", "test@email.com", "Penicilina");

        userRequest = new UserRequest("TestUser", "Test Name", "test@email.com", "Str0ngP@ssw0rd", "Penicilina");
    }

    @Test
    @DisplayName("Should login existing user")
    void login_whenFindByUsername_returnsLoad () {
        when(userRepository.findByUsername("TestUser")).thenReturn(java.util.Optional.of(user));

        UserDetails result = userService.loadUserByUsername("TestUser");

        assert(result.getUsername().equals("TestUser"));
        assert(result.getPassword().equals("$2a$12$C8CFsIqDYxhkJloBvWXetOGN..TN7gD1V.VK152fNZ9gPp5IGRXZ2"));


        verify(userRepository, times(1)).findByUsername("TestUser");
    }

    @Test
    @DisplayName("Should return user by ID")
    void getUserResponseById_whenUserExist_returnsUserResponse() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse result = userService.getUserResponseById(1L);

        assertThat(result).isEqualTo(userResponse);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should encrypts the password, verifies that the username does not already exist, save the new user and returns a response object")
    void addUser_whenUserIsValid_returnsUserRequest() {
        when(passwordEncoder.encode(userRequest.password())).thenReturn("encodedPassword");
        when(userRepository.existsByUsername(userRequest.username())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse result = userService.addUser(userRequest);

        assertThat(result).isEqualTo(userResponse);
        verify(passwordEncoder, times(1)).encode(userRequest.password());
        verify(userRepository, times(1)).existsByUsername(userRequest.username());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw an exception when trying to add a user with an existing username")
    void addUser_whenUsernameAlreadyExists_returnsEntityAlreadyExistsException() {
        when(userRepository.existsByUsername(userRequest.username())).thenReturn(true);

        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> userService.addUser(userRequest));

        assertEquals("User with username TestUser already exists", exception.getMessage());
    }

    @Test
    @DisplayName("Should update user")
    void updateUser_whenUserExists_returnsUserResponse() {
        UserRequest updateRequest = new UserRequest("new_test", "Test Updated", "new@email.com", "NewP@ssw0rd", "Aspirina");

        UserResponse expectedResponse = new UserResponse(1L, "new_test", "Test Updated", "new@email.com", "Aspirina");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername(updateRequest.username())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse result = userService.updateUser(1L, updateRequest);

        assertThat(result).isEqualTo(expectedResponse);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).existsByUsername(updateRequest.username());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should delete user by ID")
    void deleteUser_whenUserExists_returnsVoid() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}