package distribuida;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.time.LocalDateTime;

@ApplicationScoped
@Path(value = "/")
public class HolaMundo {

	@Inject
	@ConfigProperty(name = "saludo.mensaje", defaultValue = "test")
	private String msg;
	
	
	@Path(value = "/hola")
	@GET
	public String hola() {
		
		System.out.println("Proveedores de configuracion");
		
		ConfigProvider.getConfig().getConfigSources().forEach(s->{
				System.out.println(" " + s.getName());
		});
		
		
		//.1
//		Config config = ConfigProvider.getConfig();
//
//		String mensaje = config.getValue("saludo.mensaje", String.class);
//
//		String ret = String.format("%s %s", mensaje, LocalDateTime.now());
		

		String ret = String.format("%s %s", msg, LocalDateTime.now());
		
		return ret;
	}
	
}
