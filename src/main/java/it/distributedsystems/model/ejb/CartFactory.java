package it.distributedsystems.model.ejb;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Scanner;

public class CartFactory {

    private static Logger logger = Logger.getLogger("DAOFactory");
    private int number;
    private static File f = new File("cartnumber.txt");;

    public CartFactory() {
        /*try {
            String path = new java.io.File( "." ).getCanonicalPath();
            System.out.println("*********************************************");
            System.out.println("PATH: "+path);
            System.out.println("*********************************************");
            File f = new File();
        } catch (IOException e) {

        }*/
        if (!f.isFile()) {
            try {
                f.createNewFile();
                System.out.println("FILE cartnumber.txt CREATO CON SUCCESSO");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Scanner s = new Scanner(f);
            this.number = s.nextInt();
            s.close();
        } catch (Exception e) {
            number = 100;
        }
    }

    private static InitialContext getInitialContext() throws Exception {
        Hashtable props = new Hashtable();
        //props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        //props.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
        //props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming.remote.client.InitialContextFactory");
        //props.put("jboss.naming.client.ejb.context", true);
        return new InitialContext(props);
    }

    public Cart getCart() {
        try {
            InitialContext context = getInitialContext();
/*REMOTE*/  //Cart result = (Cart)context.lookup("ejb:distributed-systems-demo/distributed-systems-demo.jar/EJB3Cart!it.distributedsystems.model.ejb.Cart?stateful");
/*LOCAL*/   Cart result = (Cart)context.lookup("java:global/distributed-systems-demo/distributed-systems-demo.war/EJB3Cart!it.distributedsystems.model.ejb.Cart");
            result.setCartNumber(generateCartNumber());
            System.out.println("============= DEBUG ===================");
            System.out.println("CART (#" + result.getCartNumber() + ") ottenuto!");
            System.out.println(result.toString());
            System.out.println("=======================================");
            return result;
        } catch (Exception var3) {
            logger.error("Error looking up CART", var3);
            return null;
        }
    }

    public int generateCartNumber() {
        try {
            PrintWriter pw = new PrintWriter(f);
            pw.print(++this.number);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return number;
    }
}
