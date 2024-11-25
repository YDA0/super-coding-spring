package com.github.supercoding.web.dto.airline;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TicketResponse {
    @Schema(name = "tickets", description = "tickets 들") private List<Ticket> tickets;
}