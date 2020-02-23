package it.distributedsystems.model.ejb;

import it.distributedsystems.model.dao.Customer;
import it.distributedsystems.model.dao.Product;
import it.distributedsystems.model.dao.Purchase;
import it.distributedsystems.model.dao.PurchaseDAO;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.persistence.GeneratedValue;
import java.util.ArrayList;
import java.util.List;

@Stateful
//@Local(Cart.class)
@Remote(Cart.class)
public class EJB3Cart implements Cart {
    private Customer customer;
    @GeneratedValue
    private int cartNumber;
    private List<Purchase> purchases;
    @EJB PurchaseDAO ejb3PurchaseDAO;

    public EJB3Cart() {
        this.customer = null;
        this.cartNumber = 1;
        this.purchases = new ArrayList<Purchase>();
    }

    public EJB3Cart(Customer customer, int cartNumber) {
        this.customer = customer;
        this.cartNumber = cartNumber;
        this.purchases = new ArrayList<Purchase>();
    }

    @Override
    public List<Purchase> getPurchases() {
        return purchases;
    }

    @Override
    public int addProduct(Product p, int quantity) {
        if (p!=null && quantity>0) {
            for(Purchase purchase : purchases) {
                if (purchase.getProduct().getId() == p.getId()) {
                    purchase.setQuantity(purchase.getQuantity()+quantity);
                    return purchase.getQuantity();
                }
            }
            purchases.add(new Purchase(cartNumber, customer, p, quantity));
            return quantity;
        }
        else
            return 0;
    }

    @Override
    public int removeProduct(Product p) {
        if (p!=null) {
            for(Purchase purchase : purchases) {
                if (purchase.getProduct().getId() == p.getId()) {
                    purchases.remove(purchase);
                    return purchase.getQuantity();
                }
            }
            return 0;
        }
        else
            return 0;
    }

    @Override
    public void clearCart() {
        this.purchases.clear();
    }

    @Override
    public void submit() {
        for (Purchase p : purchases)
            ejb3PurchaseDAO.insertPurchase(p);
    }

    @Override
    public float getTotal() {
        float result = 0;
        for (Purchase purchase : purchases)
            result += purchase.getProduct().getPrice() * purchase.getQuantity();
        return result;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getCartNumber() {
        return cartNumber;
    }

    public void setCartNumber(int cartNumber) {
        this.cartNumber = cartNumber;
    }

    @Override
    public String toString() {
        return "EJB3Cart{" +
                "customer=" + customer +
                ", cartNumber=" + cartNumber +
                ", purchases=" + purchases +
                '}';
    }
}
