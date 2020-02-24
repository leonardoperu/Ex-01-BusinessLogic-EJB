package it.distributedsystems.model.dao;

//import javax.persistence.Entity;
//import javax.persistence.IdClass;
import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ProdPurch implements Serializable {

    @EmbeddedId private ProdPurchKey pkey;

    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}//,
            //fetch = FetchType.EAGER
    ) @MapsId("productId") private Product product;

    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}//,
            //fetch = FetchType.EAGER
    ) @MapsId("purchaseId") private Purchase2 purchase;

    private int quantity;

    public ProdPurch() {
        this.pkey = new ProdPurchKey();
    }

    public ProdPurch(Product prod, Purchase2 pur, int quantity) {
        this.product = prod;
        this.purchase = pur;
        this.pkey = new ProdPurchKey(prod.getId(), pur.getId());
        this.quantity = quantity;
    }

    public ProdPurchKey getPkey() {
        return pkey;
    }

    public void setPkey(ProdPurchKey pkey) {
        this.pkey = pkey;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Purchase2 getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase2 purchase) {
        this.purchase = purchase;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
