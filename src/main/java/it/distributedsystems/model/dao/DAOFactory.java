package it.distributedsystems.model.dao;

import it.distributedsystems.model.ejb.EJB3DaoFactory;

public abstract class DAOFactory {

    // ---------------------------------------------------------------------------

    public static DAOFactory getDAOFactory(String whichFactory) {
        try {
            DAOFactory result = (DAOFactory) new EJB3DaoFactory();
            System.out.println("============= DEBUG ===================");
            System.out.println("DAOFactory ottenuta!");
            System.out.println("=======================================");
            return result;  //Class.forName(whichFactory).newInstance();
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    // ---------------------------------------------------------------------------

    public abstract CustomerDAO getCustomerDAO();

    public abstract PurchaseDAO getPurchaseDAO();

    public abstract ProductDAO getProductDAO();

    public abstract ProducerDAO getProducerDAO();

    public abstract ProdPurchDAO getProdPurchDAO();
}
