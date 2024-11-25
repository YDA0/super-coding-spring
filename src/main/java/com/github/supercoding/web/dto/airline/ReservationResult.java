package com.github.supercoding.web.dto.airline;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResult {
    @Schema(name = "prices", description = "Flight 가격들", example = "[\"12000\", \"3000\"]") private List<Integer> prices;
    @Schema(name = "charges", description = "Flight 추가 비용들", example = "[\"100000\", \"30000\"]") private List<Integer> charges;
    @Schema(name = "tax", description = "항공권 세금", example = "12300") private Integer tax;
    @Schema(name = "totalPrice", description = "모든 비용 총합", example = "12500")private Integer totalPrice;
    @Schema(name = "success", description = "예약 성공 상태", example = "true")private Boolean success;
}
