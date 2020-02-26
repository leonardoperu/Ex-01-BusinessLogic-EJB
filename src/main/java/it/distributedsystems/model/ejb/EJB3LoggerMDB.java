package it.distributedsystems.model.ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(
                        propertyName = "destinationType",
                        propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(
                        propertyName = "destinationLookup",
                        propertyValue = ("java:/jms/queue/loggingQueue"))
        }
)
public class EJB3LoggerMDB implements MessageListener {

    private static File f = new File("C:\\Users\\Leonardo\\SistDistribuiti\\Ex-01-BusinessLogic-EJB\\src\\log\\log.txt");

    @Override
    public void onMessage(Message message) {

        if (!f.isFile())
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        try {
            String text = ((TextMessage) message).getText();
            System.out.println(text);
            PrintWriter pw = new PrintWriter(new FileWriter(f, true));
            pw.println(text);
            //pw.flush();
            pw.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
