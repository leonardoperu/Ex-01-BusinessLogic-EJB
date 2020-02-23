package it.distributedsystems.model.dao;

import javax.persistence.*;
import java.io.Serializable;

/*
* Purchase inteso come acquisto di un prodotto (in quantità X) da parte di un cliente.
* Ogni ordine (contenuto di un carrello acquistato) contiene N Purchase.
* Ogni Purchase ha il proprio id. Purchase nello stesso ordine hanno lo stesso purchase number.
*
* */

@Entity
public class Purchase implements Serializable {

    private static final long serialVersionUID = 4612874195612951296L;

    protected int id;
    protected int purchaseNumber;
    protected Customer customer;
    protected Product product;
    protected int quantity;

    public Purchase() {}

    public Purchase(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public Purchase(int purchaseNumber, Customer customer) {
        this.purchaseNumber = purchaseNumber;
        this.customer = customer;
    }

    public Purchase(int purchaseNumber, Customer customer, Product product, int quantity) {
        this.purchaseNumber = purchaseNumber;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //@Column(unique = true)
    public int getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    @ManyToOne(
            cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne(
            cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch=FetchType.LAZY
    )
    public Product getProduct() { return product; }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}