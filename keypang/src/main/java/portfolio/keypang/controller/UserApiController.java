package portfolio.keypang.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import portfolio.keypang.controller.dto.UserDto;
import portfolio.keypang.service.UserService;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void userSave(@RequestBody @Valid UserDto.UserSaveRequest request){
        userService.save(request);
    }


}
