package com.example.book_shops.repository;




import com.example.book_shops.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);   // use to delete all the cart id
}
