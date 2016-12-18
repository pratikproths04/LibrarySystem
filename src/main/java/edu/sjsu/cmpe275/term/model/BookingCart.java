package edu.sjsu.cmpe275.term.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

@Entity
public class BookingCart implements Serializable {
    private static final long serialVersionUID = 5865760835716664141L;
    @Id
	@Column(name = "BOOKINGCARTID")
	private String bookingCartId;
	@PrePersist
	private void generateSecret(){
		this.setBookingCartId(UUID.randomUUID().toString());
	}
    private List<CartItem> cartItems;
    private int totalQuantity;
    
    public BookingCart() {
    	cartItems = new ArrayList<CartItem>();
        totalQuantity = 0;
    }
    
	public String getBookingCartId() {
		return bookingCartId;
	}
    
	public void setBookingCartId(String bookingCartId) {
		this.bookingCartId = bookingCartId;
	}

	public synchronized List<CartItem> getCartItems() {
        return cartItems;
    }

    public synchronized void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public synchronized int getTotalQuantity() {
        return totalQuantity;
    }

    public synchronized void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public synchronized void addCartItem(CartItem cartItem) {
        for (CartItem item : cartItems) {
            if (cartItem.getBook().getIsbn().equalsIgnoreCase(item.getBook().getIsbn())){
                item.increaseQuantity();
                return;
            }
        }
        cartItems.add(cartItem);
    }

    public synchronized void removeCartItemByISBN(String id) {
        Iterator<CartItem> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getBook().getIsbn().equalsIgnoreCase(id)){
                iterator.remove();
                break;
            }
        }
    }

    public synchronized void clearCart() {
        this.cartItems.clear();
    }
}
