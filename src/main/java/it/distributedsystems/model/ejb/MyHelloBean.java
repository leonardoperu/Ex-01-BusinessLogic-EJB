package it.distributedsystems.model.ejb;

import javax.ejb.CreateException;
import javax.ejb.Stateless;
import javax.ejb.SessionContext;

@Stateless
public class MyHelloBean {
    public String helloWorld ()   {
        return "Hello from myEjb.MyHelloBean";
    }

    public void ejbCreate () throws CreateException {}
    public void ejbRemove () {}
    public void setSessionContext (SessionContext ctx) {}
    public void ejbActivate () {}
    public void ejbPassivate () {}
}