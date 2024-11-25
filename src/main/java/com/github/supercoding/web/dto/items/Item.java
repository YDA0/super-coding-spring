package com.github.supercoding.web.dto.items;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Item {
    @Schema(name = "id", description = "Items Id", example = "1") private String id;
    @Schema(name = "name", description = "Items 이름", example = "Dell XPS 15") private String name;
    @Schema(name = "type", description = "Items 기기타입", example = "Laptop") private String type;
    @Schema(name = "price", description = "Items 가격", example = "125000") private Integer price;
    private Spec spec;
}
