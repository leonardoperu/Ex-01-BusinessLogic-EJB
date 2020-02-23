package it.distributedsystems.model.ejb;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

public class CartFactory {

    private static Logger logger = Logger.getLogger("DAOFactory");

    public CartFactory() {
    }

    private static InitialContext getInitialContext() throws Exception {
        Hashtable props = new Hashtable();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        props.put("jboss.naming.client.ejb.context", true);
        return new InitialContext(props);
    }

    public Cart getCart() {
        try {
            InitialContext context = getInitialContext();
            Cart result = (Cart)context.lookup("ejb:distributed-systems-demo/distributed-systems-demo.jar/EJB3Cart!it.distributedsystems.model.ejb.Cart?stateful");
            System.out.println("============= DEBUG ===================");
            System.out.println("CART ottenuto!");
            System.out.println(result.toString());
            System.out.println("=======================================");
            return result;
        } catch (Exception var3) {
            logger.error("Error looking up CART", var3);
            return null;
        }
    }
}
