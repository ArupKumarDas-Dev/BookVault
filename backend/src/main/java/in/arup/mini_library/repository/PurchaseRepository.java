package in.arup.mini_library.repository;

import in.arup.mini_library.entity.Purchase;
import in.arup.mini_library.entity.PurchaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUserId(Long userId);

    boolean existsByUserIdAndBookIdAndStatus(
            Long userId,
            Long bookId,
            PurchaseStatus status
    );
}
