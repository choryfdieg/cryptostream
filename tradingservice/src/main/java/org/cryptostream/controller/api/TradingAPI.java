package org.cryptostream.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.cryptostream.model.entity.Balance;
import org.cryptostream.model.entity.Transaction;
import org.cryptostream.model.entity.TransactionRequest;
import org.cryptostream.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface TradingAPI {
    
    @Operation(summary = "Crear una transacción de compra o venta",
        description = "Este endpoint permite a un usuario registrar una transacción de compra o venta. " +
            "Actualiza los saldos del usuario y envía una notificación a un tópico que será leído por el servicio de notificaciones.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transacción creada exitosamente",
            content = @Content(schema = @Schema(implementation = Transaction.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida, verifique los datos proporcionados"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado o saldo insuficiente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Transaction> createTransaction(
        @Parameter(description = "Detalles de la transacción a crear", required = true)
        @RequestBody TransactionRequest request);
    
    @Operation(summary = "Obtener todas las transacciones",
        description = "Este endpoint devuelve un listado de todas las transacciones registradas en la aplicación.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de transacciones obtenido exitosamente",
            content = @Content(schema = @Schema(implementation = Transaction.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Transaction>> getAllTransactions();
    
    
    @Operation(summary = "Obtener transacciones por ID de usuario",
        description = "Este endpoint devuelve un listado de transacciones realizadas por un usuario específico, identificado por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de transacciones obtenido exitosamente",
            content = @Content(schema = @Schema(implementation = Transaction.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado o no tiene transacciones"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Transaction>> getTransactionsById(
        @Parameter(description = "ID del usuario para el cual se desean obtener las transacciones", required = true)
        @PathVariable Integer userId);
    
    
    @Operation(summary = "Obtener balances de un usuario",
        description = "Este endpoint devuelve un listado de balances de todas las monedas para un usuario específico, identificado por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de balances obtenido exitosamente",
            content = @Content(schema = @Schema(implementation = Balance.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado o no tiene balances"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Balance>> getBalancesByUserId(
        @Parameter(description = "ID del usuario para el cual se desean obtener los balances", required = true)
        @PathVariable Integer userId);
}