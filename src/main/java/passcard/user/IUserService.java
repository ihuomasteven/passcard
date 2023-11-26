package passcard.user;

import org.springframework.data.domain.Pageable;
import passcard.shared.response.ServiceResponse;
import passcard.user.dtos.AuthDto;
import passcard.user.dtos.UserDto;
import passcard.user.dtos.UserRequest;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    ServiceResponse<UserDto> authenticate(AuthDto authDto);
    ServiceResponse<List<UserDto>> getAll(Pageable pageable);
    ServiceResponse<UserDto> getById(UUID id);
    ServiceResponse<UserDto> add(UserRequest request);
    ServiceResponse<UserDto> update(UUID id, UserRequest request);
    ServiceResponse<UserDto> delete(UUID id);
}
