package Main.configurator;

public interface ProxyConfigurator {
    Object replaceWithProxy(Object t, Class implClass);
}
