package it.distributedsystems.model.ejb;

import it.distributedsystems.model.dao.Customer;
import it.distributedsystems.model.dao.Producer;
import it.distributedsystems.model.dao.Product;
import it.distributedsystems.model.dao.Purchase;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.time.LocalDateTime;

public class OperationLogger {

    @AroundInvoke
    public Object sendLogToQueue(InvocationContext context) {
        try {
            Context jndiContext = new InitialContext();
            QueueConnectionFactory factory = (QueueConnectionFactory) jndiContext.lookup("java:/ConnectionFactory");
            Queue loggingQueue = (Queue) jndiContext.lookup("java:/jms/queue/loggingQueue");
            QueueConnection connection = factory.createQueueConnection();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueSender sender = session.createSender(loggingQueue);

            context.proceed();
            connection.start();

            String operationBean = context.getMethod().getDeclaringClass().getSimpleName();
            Object param = context.getParameters()[0];

            TextMessage message = session.createTextMessage();
            String content = "[" + LocalDateTime.now().toString() + "]: " + operationBean + " inserted";
            switch (operationBean) {
                case "EJB3CustomerDAO":
                    content += " CUSTOMER " + ((Customer) param).getName() + " [id: " + ((Customer) param).getId() + "] ";
                    break;
                case "EJB3ProductDAO":
                    content += " PRODUCT " + ((Product) param).getName() + " [id: " + ((Product) param).getId() + "] ";
                    break;
                case "EJB3ProducerDAO":
                    content += " PRODUCER " + ((Producer) param).getName() + " [id: " + ((Producer) param).getId() + "] ";
                    break;
                case "EJB3PurchaseDAO":
                    content += " PURCHASE (" + ((Purchase) param).getCustomer().getName() + ","
                            + ((Purchase) param).getProduct().getName() + ") [id: " + ((Purchase) param).getId() + "] ";
                    break;
            }
            content += "into the DB.";
            message.setText(content);
            sender.send(message);
            session.close();
            return context.proceed();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
