package com.github.supercoding.web.controller;

import com.github.supercoding.service.ElectronicStoreItemService;
import com.github.supercoding.web.dto.items.BuyOrder;
import com.github.supercoding.web.dto.items.Item;
import com.github.supercoding.web.dto.items.ItemBody;
import com.github.supercoding.web.dto.items.StoreInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ElectronicStoreController {
//    @Slf4j -> 같은 내용
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ElectronicStoreItemService electronicStoreItemService;

    // 1. 모든 아이템 조회 (GET)
    @Operation(summary = "모든 Items 검색")
    @GetMapping("/items")
    public List<Item> findAllItem() {
        List<Item> items = electronicStoreItemService.findAllItem();
        return items;
    }

    // 2. 새로운 아이템 등록 (POST)
    @Operation(summary = "단일 Item 등록")
    @PostMapping("/items")
    public String registerItem(@RequestBody ItemBody itemBody){
        Integer itemId = electronicStoreItemService.saveItem(itemBody);
        return "ID: " + itemId;
    }

    // 3. ID Path로 아이템 조회 (GET)
    @Operation(summary = "단일 Item id로 검색")
    @GetMapping("/items/{id}")
    public Item findItemByPathId(
            @Parameter(name = "id", description = "item ID", example = "1")
            @PathVariable String id){
        Item item = electronicStoreItemService.findItemById(id);
        return item;
    }

    // 4. 쿼리 파라미터로 ID 조회
    @Operation(summary = "단일 Item id로 검색 (쿼리문)")
    @GetMapping("/items-query")
    public Item findItemByQueryId(
            @Parameter(name = "id", description = "item ID", example = "1")
            @RequestParam("id") String id){
        Item item = electronicStoreItemService.findItemById(id);
        return item;
    }

    // 5. 여러 ID 쿼리 파라미터 조회 (GET)
    @Operation(summary = "여러 Item ids로 검색 (쿼리문)")
    @GetMapping("/items-queries")
    public List<Item> findItemByQueryIds(
            @Parameter(name = "ids", description = "item IDs", example = "[1,2,3]")
            @RequestParam("id") List<String> ids){
        List<Item> items = electronicStoreItemService.findItemsByIds(ids);
        return items;
    }

    // 6. Path ID로 아이템 삭제 (DELETE)
    @Operation(summary = "단일 Item id로 삭제")
    @DeleteMapping("/items/{id}")
    public String deleteItemById(
            @Parameter(name = "id", description = "item ID", example = "1")
            @PathVariable String id){
        electronicStoreItemService.deleteItem(id);
        String responseMessage = "Object with id = " + id + " has been deleted";
        return responseMessage;
    }

    // 7. Path ID와 Body로 업데이트 (UPDATE)
    @Operation(summary = "단일 Item id로 수정")
    @PutMapping("/items/{id}")
    public Item updateItem(@PathVariable String id, @RequestBody ItemBody itemBody){
        Item updatedItem = electronicStoreItemService.updateItem(id, itemBody);
        return updatedItem;
    }

    @Operation(summary = "단일 Item 구매")
    @PostMapping("/items/buy")
    public String buyItem(@RequestBody BuyOrder buyOrder){
        Integer orderItemNums = electronicStoreItemService.buyItems(buyOrder);
        String responseMessage = "요청하신 Item 중 " + orderItemNums + "개를 구매 하였습니다.";
        return responseMessage;
    }

    @Operation(summary = "type 검색")
    @GetMapping("/items-types")
    public List<Item> findItemByTypes(@RequestParam("type") List<String> types){
        log.info("GET /items-type 요청이 들어왔습니다.");
        List<Item> items = electronicStoreItemService.findItemsByTypes(types);
        log.info("GET /items-type 응답: " + items);
        return items;
    }

    @Operation(summary = "가장 싼 거부터 검색)")
    @GetMapping("/items-prices")
    public List<Item> findItemByPrices(@RequestParam("max") Integer maxValue){
        List<Item> items = electronicStoreItemService.findItemOderByPrices(maxValue);
        return items;
    }

    @Operation(summary = "pagenation 지원")
    @GetMapping("/items-page")
    public Page<Item> findItemPagination(Pageable pageable){
        Page<Item> items = electronicStoreItemService.findAllWithPageable(pageable);
        return items;
    }

    @Operation(summary = "pagenation 지원2")
    @GetMapping("/items-types-page")
    public Page<Item> findItemPagination(@RequestParam("type") List<String> types, Pageable pageable){
        Page<Item> items = electronicStoreItemService.findAllWithPageable(types, pageable);
        return items;
    }

    @Operation(summary = "전체 stores 정보 검색")
    @GetMapping("/stores")
    public List<StoreInfo> findAllStoreInfo(){
        List<StoreInfo> storeInfos = electronicStoreItemService.findAllStoreInfo();
        return storeInfos;
    }
}
