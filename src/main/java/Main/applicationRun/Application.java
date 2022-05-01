package Main.applicationRun;

import Main.applicationContext.ApplicationContext;
import Main.config.JavaConfig;
import Main.objectFactory.ObjectFactory;

import java.util.Map;

public class Application {
    public static ApplicationContext run(String packageToScan, Map<Class, Class> interfacesToImplementation) {
        JavaConfig config = new JavaConfig(packageToScan, interfacesToImplementation);
        ApplicationContext applicationContext = new ApplicationContext(config);
        ObjectFactory objectFactory = new ObjectFactory(applicationContext);
        applicationContext.setObjectFactory(objectFactory);

        return applicationContext;
    }
}
