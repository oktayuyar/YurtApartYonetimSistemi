/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.config;

import java.math.BigInteger;
import java.security.MessageDigest;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author oktay
 */
public class config {

    private ApplicationContext applicationContext;
    private static config provider;

    public config() throws ExceptionInInitializerError {
        try {
            this.applicationContext = new ClassPathXmlApplicationContext("appContex.xml");
        } catch (BeansException ex) {
            System.err.print("Hata " + ex);
        }
    }

    public synchronized static config getInstance() throws ExceptionInInitializerError {
        config tempProvider;
        if (provider == null) {
            provider = new config();
            tempProvider = provider;
        } else if (provider.getApplicationContext() == null) {
            provider = new config();
            tempProvider = provider;
        } else {
            tempProvider = provider;
        }

        return tempProvider;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static String MD5(String gelen) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] message = md.digest(gelen.getBytes());
            BigInteger number = new BigInteger(1, message);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
        public static String SHA1(String gelen){
            try {
                MessageDigest md = MessageDigest.getInstance("SHA1");
                byte[] message = md.digest(gelen.getBytes());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < message.length; i++) {
                    sb.append(Integer.toString((message[i]& 0xff)+0x100,16).substring(1));
                }
                return sb.toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
}
