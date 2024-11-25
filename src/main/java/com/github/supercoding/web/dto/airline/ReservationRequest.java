package com.github.supercoding.web.dto.airline;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Getter
public class ReservationRequest {
    @Schema(name = "userId", description = "유저 ID", example = "1") private Integer userId;
    @Schema(name = "airlineTicketId", description = "항공편 ID", example = "2") private Integer airlineTicketId;

}
