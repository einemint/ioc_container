package Main.config;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {
    @Getter
    private Reflections scanner;
    private Map<Class, Class> typeToImplementationClass;

    public JavaConfig(String packageToScan, Map<Class, Class> typeToImplementationClass) {
        this.scanner = new Reflections(packageToScan);
        this.typeToImplementationClass = typeToImplementationClass;
    }
    @Override
    public <T> Class<? extends T> getImplClass(Class<T> type) {
        return typeToImplementationClass.computeIfAbsent(type, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(type);
            if (classes.size() != 1) {
                throw new RuntimeException(type + " has 0 or more then 1 implementations please update your config");
            }
            return classes.iterator().next();
        });
    }
}
