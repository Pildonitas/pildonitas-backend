package pildonitas.pdnt.user.dto;

public record UserResponse(
        Long id,
        String username,
        String name,
        String email,
        String allergies
) {
}