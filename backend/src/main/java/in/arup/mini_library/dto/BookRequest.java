package in.arup.mini_library.dto;

import java.math.BigDecimal;

public record BookRequest(
        String title,
        String author,
        String description,
        BigDecimal price,
        boolean paid,
        String coverImage
) {
}
