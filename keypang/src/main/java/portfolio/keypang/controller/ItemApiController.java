package portfolio.keypang.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import portfolio.keypang.controller.dto.ItemDto.RegisterRequest;
import portfolio.keypang.domain.users.common.annotation.CurrentUser;
import portfolio.keypang.service.ItemService;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/items")
public class ItemApiController {

  private final ItemService itemService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void register(@CurrentUser String uniqueId, @RequestBody @Valid RegisterRequest request,
      @RequestPart(required = false) MultipartFile imagePath) {
    itemService.register(uniqueId, request, imagePath);

  }

}
