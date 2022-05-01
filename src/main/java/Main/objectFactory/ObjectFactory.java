package Main.objectFactory;

import Main.applicationContext.ApplicationContext;
import Main.configurator.ObjectConfigurator;
import Main.configurator.ProxyConfigurator;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {
    private static ObjectFactory objectFactory;
    private List<ObjectConfigurator> configuratorList = new ArrayList<ObjectConfigurator>();
    private List<ProxyConfigurator> proxyConfiguratorList = new ArrayList<ProxyConfigurator>();
    private final ApplicationContext context;

    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            configuratorList.add(aClass.getDeclaredConstructor().newInstance());
        }

        for (Class<? extends ProxyConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ProxyConfigurator.class)) {
            proxyConfiguratorList.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> implClass) {
        T t = implClass.getDeclaredConstructor().newInstance();
        T finalT = t;
        configuratorList.forEach(objectConfigurator -> objectConfigurator.configure(finalT, context));

        for (Method method : implClass.getMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }

        for (ProxyConfigurator proxyConfigurator : proxyConfiguratorList) {
            t = (T) proxyConfigurator.replaceWithProxy(t, implClass);
        }

        return t;
    }
}
