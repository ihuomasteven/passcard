package passcard.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import passcard.application.Dto.request.LoginDto;
import passcard.domain.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    LoginDto toLoginDto(User user);

    @Mapping(target = "authorities", ignore = true)
    User toUser(LoginDto loginDto);
}
