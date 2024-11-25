package com.github.supercoding.web.dto.items;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BuyOrder {
    @Schema(name = "itemId", description = "Item ID", example = "1") private Integer itemId;
    @Schema(name = "itemNums", description = "Item 주문 갯수", example = "5") private Integer itemNums;
}
