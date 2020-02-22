package it.distributedsystems.model.dao;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public final class ProdPurchKey implements Serializable {

    private int productId;
    private int purchaseId;

    public ProdPurchKey() {}
    public ProdPurchKey(int productId, int purchaseId) {
        this.productId = productId;
        this.purchaseId = purchaseId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if (!(obj instanceof ProdPurchKey))
            return false;
        ProdPurchKey other = (ProdPurchKey) obj;
        if (this.productId==other.productId && this.purchaseId==other.purchaseId)
            return true;
        else return false;
    }

    public int hashCode() {
        return (purchaseId+productId);
    }

    public String toString() {
        return ""+purchaseId+"-"+productId;
    }

}
