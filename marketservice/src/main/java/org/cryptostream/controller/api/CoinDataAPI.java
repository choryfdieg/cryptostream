package org.cryptostream.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.cryptostream.model.CoinHistoryResponse;
import org.cryptostream.model.PriceResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface CoinDataAPI {
    
    @Operation(summary = "Obtener todos los precios de criptomonedas",
        description = "Este endpoint se conecta al servicio externo Coingecko y obtiene los precios en USD de Bitcoin y Solana.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Precios obtenidos exitosamente", content = @Content(schema = @Schema(implementation = PriceResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error al obtener los precios")
    })
    public ResponseEntity<PriceResponse> getAllCoinPrices();
    
    @Operation(summary = "Obtener el precio de una criptomoneda específica",
        description = "Este endpoint se conecta al servicio externo Coingecko y obtiene el precio en USD de la criptomoneda especificada por el coin_id.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Precio obtenido exitosamente", content = @Content(schema = @Schema(implementation = PriceResponse.class))),
        @ApiResponse(responseCode = "404", description = "Criptomoneda no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error al obtener el precio")
    })
    ResponseEntity<PriceResponse> getPricesByCoinId(
        @Parameter(description = "ID de la criptomoneda (ej. 'bitcoin' para BTC, 'solana' para SOL)", required = true)
        @PathVariable(name = "coin_id") String coinId);
    
    @Operation(summary = "Obtener el historial de precios de una criptomoneda",
        description = "Este endpoint se conecta al servicio externo Coingecko y obtiene el historial de precios en un rango de fechas especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Historial de precios obtenido exitosamente", content = @Content(schema = @Schema(implementation = CoinHistoryResponse.class))),
        @ApiResponse(responseCode = "404", description = "Criptomoneda no encontrada"),
        @ApiResponse(responseCode = "400", description = "Rango de fechas inválido"),
        @ApiResponse(responseCode = "500", description = "Error al obtener el historial de precios")
    })
    public ResponseEntity<CoinHistoryResponse> getCoinHistoryByCoinId(
        @Parameter(description = "ID de la criptomoneda (ej. 'bitcoin' para BTC, 'solana' para SOL)", required = true)
        @PathVariable(name = "coin_id") String coinId,
        @Parameter(description = "Fecha de inicio en formato dd-MM-yyyy (ej. '01-01-2024')", required = true)
        @RequestParam(name = "start_date")
        @DateTimeFormat(pattern = "dd-MM-yyyy") String startDate,
        @Parameter(description = "Fecha de fin en formato dd-MM-yyyy (ej. '31-01-2024')", required = true)
        @RequestParam(name = "end_date")
        @DateTimeFormat(pattern = "dd-MM-yyyy") String endDate);
}