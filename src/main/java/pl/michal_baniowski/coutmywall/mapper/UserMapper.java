package pl.michal_baniowski.coutmywall.mapper;

import org.springframework.stereotype.Service;
import pl.michal_baniowski.coutmywall.dto.UserDto;
import pl.michal_baniowski.coutmywall.entity.User;

@Service
public class UserMapper implements DtoMapper<User, UserDto> {
    @Override
    public User mapToEntity(UserDto dtoObject) {
        User user = new User();
        user.setId(dtoObject.getId());
        user.setUsername(dtoObject.getUsername());
        user.setEmail(dtoObject.getEmail());
        user.setPassword(dtoObject.getPassword());
        return user;
    }

    @Override
    public UserDto mapToDto(User entityObject) {
        UserDto userDto = new UserDto();
        userDto.setId(entityObject.getId());
        userDto.setUsername(entityObject.getUsername());
        userDto.setEmail(entityObject.getEmail());
        userDto.setPassword(entityObject.getPassword());
        return userDto;
    }
}
