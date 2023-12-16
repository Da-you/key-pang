package portfolio.keypang.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import portfolio.keypang.common.dto.PageResponse;
import portfolio.keypang.common.dto.SuccessResponse;
import portfolio.keypang.controller.dto.ItemDto;
import portfolio.keypang.controller.dto.ItemDto.ItemInfoResponse;
import portfolio.keypang.controller.dto.ItemDto.ItemListResponse;
import portfolio.keypang.controller.dto.ItemDto.ItemStockRequest;
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
  @GetMapping("/{itemId}")
  @ResponseStatus(HttpStatus.OK)
  public ItemInfoResponse getItemInfo(@CurrentUser String uniqueId,
      @PathVariable Long itemId,@RequestParam String sellerName) {
    return itemService.getItemInfoByUser(itemId, sellerName);
  }

  @GetMapping("itemList")
  @ResponseStatus(HttpStatus.OK)
  public SuccessResponse<PageResponse<ItemListResponse>> getItemList(@CurrentUser String uniqueId,
      @RequestParam String sellerName) {
    return new SuccessResponse<>(itemService.getItemListByUser(sellerName));
  }

  @GetMapping("seller/{itemId}")
  @ResponseStatus(HttpStatus.OK)
  public ItemInfoResponse getItemInfo(@CurrentUser String uniqueId,
      @PathVariable Long itemId) {
    return itemService.getItemInfo(uniqueId, itemId);
  }

  @GetMapping("seller/itemList")
  @ResponseStatus(HttpStatus.OK)
  public SuccessResponse<PageResponse<ItemListResponse>> getItemList(@CurrentUser String uniqueId) {
    return new SuccessResponse<>(itemService.getItemList(uniqueId));
  }

  @PatchMapping("/{itemId}/info")
  @ResponseStatus(HttpStatus.OK)
  public void updateItemInfo(@CurrentUser String uniqueId, @PathVariable Long itemId,
      @RequestBody @Valid RegisterRequest request) {
    itemService.updateInfo(uniqueId, itemId, request);
  }

  @PatchMapping("/{itemId}/stock")
  @ResponseStatus(HttpStatus.OK)
  public void updateItemStock(@CurrentUser String uniqueId, @PathVariable Long itemId,
      @RequestBody @Valid ItemStockRequest request) {
    itemService.addStock(uniqueId, itemId, request);
  }

  @PatchMapping("/{itemId}/image")
  @ResponseStatus(HttpStatus.OK)
  public void updateItemImage(@CurrentUser String uniqueId, @PathVariable Long itemId,
      @RequestPart(required = false) MultipartFile imagePath) {
    itemService.updateImage(uniqueId, itemId, imagePath);
  }


  @DeleteMapping("/{itemId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void unActiveItem(@CurrentUser String uniqueId, Long itemId) {
    itemService.unActiveItem(uniqueId, itemId);
  }

}
