package passcard.user;

import org.springframework.data.domain.Pageable;
import passcard.shared.response.ApiResponse;
import passcard.user.dtos.AuthDto;
import passcard.user.dtos.UserDto;
import passcard.user.dtos.UserRequest;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    ApiResponse<UserDto> authenticate(AuthDto authDto);
    ApiResponse<List<UserDto>> getAll(Pageable pageable);
    ApiResponse<UserDto> getById(UUID id);
    ApiResponse<UserDto> add(UserRequest request);
    ApiResponse<UserDto> update(UUID id, UserRequest request);
    ApiResponse<UserDto> delete(UUID id);
}
