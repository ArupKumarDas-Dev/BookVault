package in.arup.mini_library.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookResponse(
        Long id,
        String title,
        String author,
        String description,
        BigDecimal price,
        boolean paid,
        String coverImage,
        LocalDateTime createdAt
) {
}
