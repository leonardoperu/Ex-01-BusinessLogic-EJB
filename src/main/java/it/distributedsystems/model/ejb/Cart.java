package it.distributedsystems.model.ejb;

import it.distributedsystems.model.dao.Customer;
import it.distributedsystems.model.dao.Product;
import it.distributedsystems.model.dao.Purchase;

import java.util.List;

public interface Cart {
    public List<Purchase> getPurchases();
    public int addProduct(Product p, int quantity);
    public int removeProduct(Product p);
    public void clearCart();
    public void submit();
    public float getTotal();
    public Customer getCustomer();
    public void setCustomer(Customer customer);
    public int getCartNumber();
    public void setCartNumber(int cartNumber);
}
