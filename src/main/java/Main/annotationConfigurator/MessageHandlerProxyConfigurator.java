package Main.annotationConfigurator;

import Main.configurator.ProxyConfigurator;
import Main.annotation.Message;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MessageHandlerProxyConfigurator implements ProxyConfigurator {
    @Override
    public Object replaceWithProxy(Object t, Class implClass) {
        if (implClass.isAnnotationPresent(Message.class)) {
            return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("MESSAGEMESSAGEMESSAGE!");
                    return method.invoke(t);
                }
            });
        }

        else return t;
    }
}
