package com.example.bfhl.controller;

import com.example.bfhl.dto.BfhlRequest;
import com.example.bfhl.dto.BfhlResponse;
import com.example.bfhl.service.BfhlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bfhl")
@Tag(name = "BFHL Operations", description = "Endpoints for processing and sorting list-based payloads.")
@CrossOrigin(origins = "*")
public class BfhlController {

    private final BfhlService bfhlService;

    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    @PostMapping
    @Operation(
            summary = "Process and classify payload",
            description = "Separates input data array into odd numbers, even numbers, alphabets, and special characters. Calculates numerical sum and returns alternating-case reversed alphabetical combination."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Request processed successfully.",
                    content = @Content(schema = @Schema(implementation = BfhlResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input or validation error."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error occurred."
            )
    })
    public ResponseEntity<BfhlResponse> processData(@Valid @RequestBody BfhlRequest request) {
        BfhlResponse response = bfhlService.processRequest(request);
        return ResponseEntity.ok(response);
    }
}
