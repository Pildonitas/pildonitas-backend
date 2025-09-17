package pildonitas.pdnt.user.dto;

import pildonitas.pdnt.user.Role;
import pildonitas.pdnt.user.User;

public class UserMapper {
    public static User dtoToEntity(UserRequest userRequest) {
        Role role = Role.USER;
        return new User(userRequest.username(), userRequest.name(), userRequest.email(), userRequest.password(), userRequest.allergies(), role);
    }

    public static UserResponse entityToDto(User entity) {
        return new UserResponse(entity.getId(), entity.getUsername(), entity.getName(), entity.getEmail(), entity.getAllergies());
    }
}