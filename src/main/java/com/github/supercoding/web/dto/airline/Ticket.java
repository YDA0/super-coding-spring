package com.github.supercoding.web.dto.airline;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Ticket {
    @Schema(name = "depart", description = "승객 출발지", example = "서울") private String depart;
    @Schema(name = "arrival", description = "승객 도착지", example = "파리") private String arrival;
    @Schema(name = "departureTime", description = "항공권 출발시간", example = "2023-05-05 11:00:00") private String departureTime;
    @Schema(name = "returnTime", description = "항공권 귀국시간", example ="2023-05-07 11:00:00") private String returnTime;
    @Schema(name = "TicketId", description = "Ticket ID", example = "1") private Integer ticketId;
}
