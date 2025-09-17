package pildonitas.pdnt.user.dto;

import pildonitas.pdnt.user.User;

public class UserMapper {
    public static User dtoToEntity(UserRequest dto) {
        return new User(dto.username(), dto.name(), dto.email(), dto.password(), dto.allergies());
    }

    public static UserResponse entityToDto(User entity) {
        return new UserResponse(entity.getId(), entity.getUsername(), entity.getName(), entity.getEmail(), entity.getAllergies());
    }
}