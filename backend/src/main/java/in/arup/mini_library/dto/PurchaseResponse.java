package in.arup.mini_library.dto;

import in.arup.mini_library.entity.PurchaseStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PurchaseResponse(
        Long purchaseId,
        Long bookId,
        String bookTitle,
        BigDecimal amount,
        String paymentId,
        PurchaseStatus status,
        LocalDateTime purchasedAt
) {
}
