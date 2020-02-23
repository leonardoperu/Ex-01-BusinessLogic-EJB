package it.distributedsystems.model.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Purchase2 implements Serializable {

    private static final long serialVersionUID = 4612874195612951296L;

    protected int id;
    protected int purchaseNumber;
    protected Customer customer;
    protected Set<ProdPurch> products;

    public Purchase2() {}

    public Purchase2(int purchaseNumber) { this.purchaseNumber = purchaseNumber; }

    public Purchase2(int purchaseNumber, Customer customer) {
        this.purchaseNumber = purchaseNumber;
        this.customer = customer;
    }

    public Purchase2(int purchaseNumber, Customer customer, Set<ProdPurch> products) {
        this.purchaseNumber = purchaseNumber;
        this.customer = customer;
        this.products = products;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(unique = true)
    public int getPurchaseNumber() { return id; }

    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    @ManyToOne(
            cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    public Customer getCustomer() { return customer; }

    public void setCustomer(Customer customer) { this.customer = customer; }

    @OneToMany(
            cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch=FetchType.LAZY,
            mappedBy = "purchase"
    )
    public Set<ProdPurch> getProducts() { return products; }

    public void setProducts(Set<ProdPurch> products) { this.products = products; }
}