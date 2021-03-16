package distribuida;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;


@ApplicationScoped
public class InicializacionCdi {

    public static String ID = UUID.randomUUID().toString();



    @Inject
    @ConfigProperty(name = "configsource.consul.host", defaultValue = "127.0.0.1")
    public String consulHost;

    @Inject
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8080")
    public int appPort;

    @Inject
    @ConfigProperty(name = "app.name", defaultValue = "mp-registro")
    public String appName;

    public void init(@Observes @Initialized(ApplicationScoped.class)Object obt) throws UnknownHostException {
        System.out.println("**********************init");

        ConsulClient client = new ConsulClient("localhost");

        NewService newService = new NewService();
        newService.setId(ID);
        newService.setName(appName);
        newService.setPort(appPort);
        newService.setAddress(InetAddress.getLocalHost().getHostAddress());
        client.agentServiceRegister(newService);

    }
    public void destroy(@Observes @Destroyed(ApplicationScoped.class)Object obt) {
        System.out.println("***destroy");

        ConsulClient client = new ConsulClient("localhost");

        client.agentServiceDeregister(ID);

    }
}