package com.github.supercoding.web.dto.items;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Spec {
    @Schema(name = "cpu", description = "Items CPU", example = "Google Tensor") private String cpu;
    @Schema(name = "capacity", description = "Items 용량 Spec", example = "25G") private String capacity;
}
