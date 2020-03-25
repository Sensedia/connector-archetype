package $package .route;

import com.sensedia.connector_core.cache.EnvironmentComponentRepository;
import com.sensedia.connector_core.util.StringResolver;
import org.apache.camel.Processor;

import com.sensedia.connector_core.route.BaseRouterBuilder;
import com.sensedia.apimicrogateway.dtos.CallDTO;
import org.apache.camel.util.json.JsonObject;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * This class defines importants configurations to call flux of connectors
 * Like defines the in and out transformations
 */
public final class ConnectorRoute extends BaseRouterBuilder {

	/**
	 * This method defines the type of component that the connector connects to
	 * A Component is essentially a factory of Endpoint instances.
	 * @link https://camel.apache.org/component.html
	 * @return String - Return explicitly the type of component
	 */
	@Override
	public String component() {
		throw new UnsupportedOperationException("Not implemented, yet");
	}

	/**
	 * This method must manipulate the CallDTO present in exchange,
	 * and transform recepted params in complete instructions to be executed in target backend
	 * inserting the instruction on exchange body
	 * @see org.apache.camel.Exchange
	 * @return Processor - To transform the call during execution
	 * @see org.apache.camel.Processor
	 */
	@Override
	public Processor transformProcessor() {
		return exchange ->
		{
			CallDTO call = exchange.getIn().getBody(CallDTO.class);
			// resolve your call with parameters and send instruction to component
			String instruction = new StringResolver().resolve(call);
			exchange.getMessage().setBody(instruction);

			// Set o value of your connector using cache with EnvironmentComponentRepository or resolve your endpoint
			String value = EnvironmentComponentRepository.get(call.getEnvironmentId());
			exchange.getMessage().setHeader("connectorId", value);
		};
	}

	/**
	 * This method must manipulate the response of backend,
	 * add specific treatments and manipulations
	 * @return Processor - To transform the call during execution
	 * @see org.apache.camel.Processor
	 */
	@Override
	public Processor responseProcessor() {
		return exchange ->
		{
			Object body = exchange.getIn().getBody();
			if (Objects.isNull(body)) {

				JsonObject obj = new JsonObject();
				obj.put("message", "empty");
				exchange.getMessage().setBody(obj);
			}

			exchange.setProperty("statusCode", HttpServletResponse.SC_OK);
		};
	}

}