package in.arup.mini_library.controller;

import in.arup.mini_library.dto.PurchaseRequest;
import in.arup.mini_library.dto.PurchaseResponse;
import in.arup.mini_library.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<PurchaseResponse> createPurchase(
            @RequestBody PurchaseRequest request,
            Authentication authentication
    ) {

        String email = authentication.getName();

        PurchaseResponse response =
                purchaseService.createPurchase(
                        request,
                        email
                );

        return ResponseEntity.ok(response);
    }
}