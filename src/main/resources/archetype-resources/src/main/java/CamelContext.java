package $package;

import $package .route.ConnectorRoute;
import com.sensedia.apimicrogateway.dtos.CallDTO;
import io.quarkus.runtime.StartupEvent;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.quarkus.core.CamelMain;
import org.apache.camel.spi.Registry;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static com.sensedia.connector_core.route.BaseRouterBuilder.ROUTE;

/**
 * This class defines configurations of Camel context
 */
public class CamelContext {

	@Inject
	CamelMain runtime;

	ProducerTemplate producer;

	/**
	 * This method defines on initialization which Route configurations
	 * It must declare on Camel Runtime which ConnectorRoute should be used
	 * @param event
	 * @throws Exception
	 */
	public void initialized(@Observes StartupEvent event) throws Exception {
		runtime.getCamelContext().addRoutes(new ConnectorRoute());
	}

	/**
	 * This method create a Exchange based on CallDTO and configure it on Camel Runtime in ROUTE
	 * @param call - Object to be configured on Camel Runtime
	 * @return
	 */
	public Exchange produce(final CallDTO call) {

		if (producer == null) {
			producer = runtime.getCamelContext().createProducerTemplate();
		}

		return producer.send(ROUTE, getExchange(call));
	}

	/**
	 * This method configure a Exchange to use CallDTO as a body
	 * @param body
	 * @return
	 */
	private final Exchange getExchange(final CallDTO body) {
		return ExchangeBuilder.anExchange(runtime.getCamelContext())
				              .withBody(body)
				              .build();
	}

	/**
	 * Camel supports a pluggable Registry plugin strategy. This allows Camel to easily work with some kind of registry
	 * @see 'https://camel.apache.org/manual/latest/registry.html'
	 */
	public Registry getRegistry(){
		return runtime.getCamelContext().getRegistry();
	}
}