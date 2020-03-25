package $package .service;

import $package .BaseIntegrationTest;
import com.sensedia.apimicrogateway.models.Environment;
import com.sensedia.apimicrogateway.models.Property;
import com.sensedia.commonsconnector.dto.EnvironmentConnectionDTO;
import com.sensedia.commonsconnector.enums.ConnectionStatus;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@QuarkusTest
public class ConnectorServiceTest extends BaseIntegrationTest {

    @Inject
    ConnectorServiceImpl connectorService;

    //    @Test
    public void validateTestConnectionSuccess() {
        List<Property> properties = new ArrayList<>();
        properties.add(Property.builder().key("host").value(host).build());
        properties.add(Property.builder().key("port").value(port).build());

        Environment environment = Environment.builder()
                .environmentId(ENVIRONMENT_ID)
                .properties(properties)
                .build();

        Collection<EnvironmentConnectionDTO> connections = connectorService.testConnection(Collections.singletonList(environment));

        Assertions.assertEquals(1, connections.size());
        Assertions.assertTrue(connections.stream()
                .map(EnvironmentConnectionDTO::getStatus)
                .allMatch(ConnectionStatus.OK::equals));
    }

    //    @Test
    public void validateTestConnectionError() {
        List<Property> properties = new ArrayList<>();
        properties.add(Property.builder().key("host").value("asd").build());
        properties.add(Property.builder().key("port").value(port).build());

        Environment environment = Environment.builder()
                .environmentId(ENVIRONMENT_ID)
                .properties(properties)
                .build();

        Collection<EnvironmentConnectionDTO> connections = connectorService.testConnection(Collections.singletonList(environment));

        Assertions.assertEquals(1, connections.size());
        Assertions.assertTrue(connections.stream()
                .map(EnvironmentConnectionDTO::getStatus)
                .allMatch(ConnectionStatus.ERROR::equals));
    }
}