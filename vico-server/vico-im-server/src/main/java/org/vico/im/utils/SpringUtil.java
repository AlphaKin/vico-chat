package org.vico.im.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("SpringUtil")
public class SpringUtil implements ApplicationContextAware {
    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public <T> T getBean(String beanName){
        if(context.containsBean(beanName)){
            return (T) context.getBean(beanName);
        }
        return null;
    }

}
