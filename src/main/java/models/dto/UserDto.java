package models.dto;

import lombok.Getter;

@Getter
public class UserDto {

    private final String username;
    private final String password;

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("username:'%s', password: '%s'", username, password);
    }
}
