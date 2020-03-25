package $package .service;

import com.sensedia.apimicrogateway.dtos.CallDTO;
import com.sensedia.apimicrogateway.dtos.ResponseDTO;
import com.sensedia.apimicrogateway.exceptions.ConnectorException;
import com.sensedia.apimicrogateway.models.Environment;
import com.sensedia.apimicrogateway.services.ConnectorService;
import com.sensedia.commonsconnector.dto.BodyDTO;
import com.sensedia.commonsconnector.dto.EnvironmentConnectionDTO;
import com.sensedia.commonsconnector.dto.PropertyDTO;
import com.sensedia.commonsconnector.dto.StructureDetailDTO;
import com.sensedia.connector_core.util.PropertyResolver;

import org.apache.camel.Exchange;

import javax.ws.rs.core.HttpHeaders;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import $package .model.Property;

import static com.sensedia.connector_core.route.BaseRouterBuilder.STATUS_CODE;

import $package .CamelContext;

/**
 * This is the principal class of a Connector
 * It Implements the principals functionalitys of Connector, without it, nothing will works
 * 	@version 1.0
 *	@see ConnectorService
 */
@ApplicationScoped
public class ConnectorServiceImpl implements ConnectorService {

	private static final Logger LOGGER = Logger.getLogger(ConnectorServiceImpl.class.getName());

	@Inject
	CamelContext camel;

	/**
	 * This method receives a Call from the connector,
	 * makes an external call to the target service,
	 * and handles callback to the connector
	 * @param call - Object recepted of Call on Connector
	 */
	@Override
	public void execute(CallDTO call) {

		Exchange exchange = camel.produce(call);
		String body = exchange.getMessage().getBody(String.class);

		BodyDTO bodyDTO = new BodyDTO();
		bodyDTO.setString(body, Charset.defaultCharset());

		Map<String, Object> headers = new HashMap<>();
		headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

		Integer statusCode = exchange.getProperty(STATUS_CODE, Integer.class);
		if (Objects.isNull(statusCode)) {
			throw new ConnectorException("Required status code!");
		}

		ResponseDTO response = ResponseDTO.builder()
		                                  .body(bodyDTO)
		                                  .statusCode(statusCode)
				                          .headers(headers)
				                          .build();

		call.setResponse(response);
	}

	/**
	 * This method creates environments for connectors that must maintain a connection pool open during their lifetime,
	 * using the Environment, you can create pools for each in specific
	 * This method is not required if this connector do not need a connection pool or something like that
	 * @param environment - This parameter allows the creation of configurations of connections and the like, in environments
	 * @throws ConnectorException
	 */
	@Override
	public void create(final List<Environment> environment) {
		throw new UnsupportedOperationException("Not implemented, yet");
	}


	/**
	 * This method updates the configurations of connections and the like, in envinronments
	 * It gets the old list environments and updates by the new list informed
	 * @param newEnvironments -  this parameter is the new configurations of connections.
	 * @param oldEnvironments - this parameter is the old configuartions of connections.
	 * @throws ConnectorException - Any error in method must been return on ConnectorException
	 */
	@Override
	public void update(final List<Environment> newEnvironments, final List<Environment> oldEnvironments) {
		throw new UnsupportedOperationException("Not implemented, yet");
	}


	/**
	 * This method remove the configuarions by the environments informed
	 * @param environments - this parameter is the list the environmets to be remove
	 */
	@Override
	public void remove(List<Environment> environments) {
		throw new UnsupportedOperationException("Not implemented, yet");
	}

	/**
	 * This method feeds a controller in which it returns a basic structure of how back-end objects the Connector connects to
	 * It should return important back-end information for later to be used in resources and operations
	 * @param environment - Each environment may have a different endpoint, so structures may vary,
	 *                       and are returned based on the Environment
	 * @return - It must retorn a Collection of StructureDatailDTO, which has important fields to fill out
	 * @see StructureDetailDTO
	 * @throws Exception - Any error in method must been return on ConnectorException
	 */
	@Override
	public Collection<StructureDetailDTO> getStructure(List<Environment> environment) throws ConnectorException {
		throw new UnsupportedOperationException("Not implemented, yet");
	}


	/**
	 *	This method return the properties configurated on connector by class defined, like properties of connection
	 *
	 * @see -  See a example in class Property.
	 *
	 * @return PropertyDTO
	 * @see PropertyDTO
	 * @throws ConnectorException
	 */
	@Override
	public Collection<PropertyDTO> getProperties() throws ConnectorException {
		return PropertyResolver.getConfigProperties(Property.class);
	}

	/**
	 * This method test connection to end-point of connector
	 * @param environments - It should receive information
	 *                      to establish a connection to a specific endpoint of the type that the Connector receives
	 * @see Environment
	 * @return EnvironmentConnectionDTO - return status, message for each environment
	 * @throws ConnectorException - Any error in method must been return on ConnectorException
	 */
	@Override
	public Collection<EnvironmentConnectionDTO> testConnection(List<Environment> environments) throws ConnectorException {
		throw new UnsupportedOperationException("Not implemented, yet");
	}

}