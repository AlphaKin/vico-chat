package org.vico.im;
import lombok.val;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.vico.im.core.ImServer;


public class Application {

    public static void main(String[] args) {
        val context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ((ImServer) context.getBean("ImServer")).start(32952);
    }
}
