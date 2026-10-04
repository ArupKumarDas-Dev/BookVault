package in.arup.mini_library.service;

import in.arup.mini_library.dto.PurchaseRequest;
import in.arup.mini_library.dto.PurchaseResponse;
import in.arup.mini_library.entity.Book;
import in.arup.mini_library.entity.Purchase;
import in.arup.mini_library.entity.PurchaseStatus;
import in.arup.mini_library.entity.User;
import in.arup.mini_library.repository.BookRepository;
import in.arup.mini_library.repository.PurchaseRepository;
import in.arup.mini_library.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public PurchaseService(
            PurchaseRepository purchaseRepository,
            BookRepository bookRepository,
            UserRepository userRepository
    ) {
        this.purchaseRepository = purchaseRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public PurchaseResponse createPurchase(
            PurchaseRequest request,
            String email
    ) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() ->
                        new RuntimeException("Book not found")
                );
        if (!book.isPaid()) {
            throw new RuntimeException("This book is free");
        }
        boolean alreadyPurchased =
                purchaseRepository.existsByUserIdAndBookIdAndStatus(
                        user.getId(),
                        book.getId(),
                        PurchaseStatus.SUCCESS
                );

        if (alreadyPurchased) {
            throw new RuntimeException(
                    "You have already purchased this book"
            );
        }
        Purchase purchase = new Purchase();

        purchase.setUser(user);
        purchase.setBook(book);
        purchase.setAmount(book.getPrice());

        purchase.setStatus(PurchaseStatus.PENDING);

        Purchase savedPurchase =
                purchaseRepository.save(purchase);

        return new PurchaseResponse(
                savedPurchase.getId(),
                book.getId(),
                book.getTitle(),
                savedPurchase.getAmount(),
                savedPurchase.getPaymentId(),
                savedPurchase.getStatus(),
                savedPurchase.getPurchasedAt()
        );
    }
}