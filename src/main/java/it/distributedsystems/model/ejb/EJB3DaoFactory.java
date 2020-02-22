package it.distributedsystems.model.ejb;

import it.distributedsystems.model.dao.*;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

public class EJB3DaoFactory extends DAOFactory {
    private static Logger logger = Logger.getLogger("DAOFactory");
    //@EJB private EJB3CustomerDAO CustomerDAO;

    public EJB3DaoFactory() {
    }

    private static InitialContext getInitialContext() throws Exception {
        Hashtable props = getInitialContextProperties();
        return new InitialContext(props);
    }

    private static Hashtable getInitialContextProperties() {
        Hashtable props = new Hashtable();
        //props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");

        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        //16:36:15,572 INFO  [org.wildfly.naming] (default task-1) WFNAM00025:
        // org.jboss.naming.remote.client.InitialContextFactory is deprecated;
        // new applications should use org.wildfly.naming.client.WildFlyInitialContextFactory instead
        props.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        props.put("jboss.naming.client.ejb.context", true);

        /*props.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        props.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
        props.put("java.naming.provider.url", "127.0.0.1:1099");*/   //(new ServerInfo()).getHostAddress()  --- 127.0.0.1 --
        return props;
    }

    public CustomerDAO getCustomerDAO() {
        try {
            InitialContext context = getInitialContext();
            //CustomerDAO result = (CustomerDAO)context.lookup("java:global/distributed-systems-demo/distributed-systems-demo.war/EJB3CustomerDAO!it.distributedsystems.model.dao.CustomerDAO");
            CustomerDAO result = (CustomerDAO)context.lookup("ejb:distributed-systems-demo/distributed-systems-demo.jar/EJB3CustomerDAO!it.distributedsystems.model.dao.CustomerDAO");
            System.out.println("============= DEBUG ===================");
            System.out.println("CustomerDAO ottenuto!");
            System.out.println(result.toString());
            System.out.println("=======================================");
            /* java:global/distributed-systems-demo/distributed-systems-demo.jar/EJB3CustomerDAO!it.distributedsystems.model.dao.CustomerDAO
	        java:app/distributed-systems-demo.jar/EJB3CustomerDAO!it.distributedsystems.model.dao.CustomerDAO
	        java:module/EJB3CustomerDAO!it.distributedsystems.model.dao.CustomerDAO
	        ejb:distributed-systems-demo/distributed-systems-demo.jar/EJB3CustomerDAO!it.distributedsystems.model.dao.CustomerDAO
	        java:global/distributed-systems-demo/distributed-systems-demo.jar/EJB3CustomerDAO
	        java:app/distributed-systems-demo.jar/EJB3CustomerDAO
	        java:module/EJB3CustomerDAO */
            return result;
        } catch (Exception var3) {
            logger.error("Error looking up EJB3CustomerDAO", var3);
            return null;
        }
    }

    /*public CustomerDAO getCustomerDAO() {
        return this.CustomerDAO;
    }*/

    public PurchaseDAO getPurchaseDAO() {
        try {
            InitialContext context = getInitialContext();
            //PurchaseDAO result = (PurchaseDAO)context.lookup("distributed-systems-demo/EJB3PurchaseDAO/local");
            PurchaseDAO result = (PurchaseDAO)context.lookup("ejb:distributed-systems-demo/distributed-systems-demo.jar/EJB3PurchaseDAO!it.distributedsystems.model.dao.PurchaseDAO");
            System.out.println("============= DEBUG ===================");
            System.out.println("PurchaseDAO ottenuto!");
            System.out.println(result.toString());
            System.out.println("=======================================");
            /*java:global/distributed-systems-demo/distributed-systems-demo.jar/EJB3PurchaseDAO!it.distributedsystems.model.dao.PurchaseDAO
	        java:app/distributed-systems-demo.jar/EJB3PurchaseDAO!it.distributedsystems.model.dao.PurchaseDAO
	        java:module/EJB3PurchaseDAO!it.distributedsystems.model.dao.PurchaseDAO
	        ejb:distributed-systems-demo/distributed-systems-demo.jar/EJB3PurchaseDAO!it.distributedsystems.model.dao.PurchaseDAO
	        java:global/distributed-systems-demo/distributed-systems-demo.jar/EJB3PurchaseDAO
	        java:app/distributed-systems-demo.jar/EJB3PurchaseDAO
	        java:module/EJB3PurchaseDAO*/
            return result;
        } catch (Exception var3) {
            logger.error("Error looking up EJB3PurchaseDAO", var3);
            return null;
        }
    }

    public ProductDAO getProductDAO() {
        try {
            InitialContext context = getInitialContext();
            //ProductDAO result = (ProductDAO)context.lookup("distributed-systems-demo/EJB3ProductDAO/local");
            ProductDAO result = (ProductDAO)context.lookup("ejb:distributed-systems-demo/distributed-systems-demo.jar/EJB3ProductDAO!it.distributedsystems.model.dao.ProductDAO");
            System.out.println("============= DEBUG ===================");
            System.out.println("ProductDAO ottenuto!");
            System.out.println(result.toString());
            System.out.println("=======================================");
            /*
            java:global/distributed-systems-demo/distributed-systems-demo.jar/EJB3ProductDAO!it.distributedsystems.model.dao.ProductDAO
	        java:app/distributed-systems-demo.jar/EJB3ProductDAO!it.distributedsystems.model.dao.ProductDAO
	        java:module/EJB3ProductDAO!it.distributedsystems.model.dao.ProductDAO
	        ejb:distributed-systems-demo/distributed-systems-demo.jar/EJB3ProductDAO!it.distributedsystems.model.dao.ProductDAO
	        java:global/distributed-systems-demo/distributed-systems-demo.jar/EJB3ProductDAO
	        java:app/distributed-systems-demo.jar/EJB3ProductDAO
	        java:module/EJB3ProductDAO
             */
            return result;
        } catch (Exception var3) {
            logger.error("Error looking up EJB3ProductDAO", var3);
            return null;
        }
    }

    public ProducerDAO getProducerDAO() {
        try {
            InitialContext context = getInitialContext();
            /*//ProducerDAO result = (ProducerDAO)context.lookup("distributed-systems-demo/EJB3ProducerDAO/local");*/
            ProducerDAO result = (ProducerDAO)context.lookup("ejb:distributed-systems-demo/distributed-systems-demo.jar/EJB3ProducerDAO!it.distributedsystems.model.dao.ProducerDAO");
            System.out.println("============= DEBUG ===================");
            System.out.println("ProducerDAO ottenuto!");
            System.out.println(result.toString());
            System.out.println("=======================================");
            /* java:global/distributed-systems-demo/distributed-systems-demo.jar/EJB3ProducerDAO!it.distributedsystems.model.dao.CustomerDAO
	        java:app/distributed-systems-demo.jar/EJB3ProducerDAO!it.distributedsystems.model.dao.CustomerDAO
	        java:module/EJB3ProducerDAO!it.distributedsystems.model.dao.CustomerDAO
	        ejb:distributed-systems-demo/distributed-systems-demo.jar/EJB3ProducerDAO!it.distributedsystems.model.dao.CustomerDAO
	        java:global/distributed-systems-demo/distributed-systems-demo.jar/EJB3ProducerDAO
        	java:app/distributed-systems-demo.jar/EJB3ProducerDAO
        	java:module/EJB3ProducerDAO */
            return result;
        } catch (Exception var3) {
            logger.error("Error looking up EJB3ProducerDAO", var3);
            return null;
        }
    }

    @Override
    public ProdPurchDAO getProdPurchDAO() {
        return null;
    }
}
