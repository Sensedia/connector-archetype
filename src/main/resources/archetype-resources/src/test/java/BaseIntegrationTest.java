package $package;

import com.sensedia.commonsconnector.dto.EnvironmentDTO;
import com.sensedia.commonsconnector.dto.PropertyDTO;
import com.sensedia.commonsconnector.dto.ScenarioDTO;
import com.sensedia.commonsconnector.enums.TransactionType;
import com.sensedia.commonsjwt.dto.TokenToBuildDTO;
import com.sensedia.commonsjwt.util.JWTUtils;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;

import com.sensedia.apimicrogateway.constants.Constants;
import static io.restassured.RestAssured.given;
import static io.undertow.util.Headers.AUTHORIZATION_STRING;

@QuarkusTest
public class BaseIntegrationTest {

    // Change the variables for real values
    // example class Property
    protected static String host = "localhost";
    protected static String port = "8080";

    // Configs JWT
    protected static final Integer JWT_ID = 1;
    protected static final Long ENVIRONMENT_ID = 1L;
    protected static final String CONNECTOR_ID_TEST_DEFAULT = "sensedia";
    protected static final String CONNECTOR_FACTOR_TEST_DEFAULT = "sensedia";
    protected static final String CHECKSUM_DEFAULT = "sensedia";

    private static final String SCENARIOS_BASE_PATH = Constants.APPLICATION_PATH.concat("/scenarios/");

    @BeforeEach
    public void createScenario() {
        createScenarioConnector();
    }

    @Test
    protected void createScenarioConnector() {
        EnvironmentDTO environmentDTO = new EnvironmentDTO();
        environmentDTO.setEnvironmentId(ENVIRONMENT_ID);
        environmentDTO.setProperties(new ArrayList<>());
        environmentDTO.getProperties().add(PropertyDTO.builder().key("host").value(host).build());
        environmentDTO.getProperties().add(PropertyDTO.builder().key("port").value(port).build());

        ScenarioDTO scenario = ScenarioDTO.builder()
                .info("Scenario Info")
                .timeout(3)
                .environments(Collections.singletonList(environmentDTO))
                .checksum(CHECKSUM_DEFAULT)
                .build();

        given()
                .body(scenario)
                .contentType(ContentType.JSON)
                .header(AUTHORIZATION_STRING, buildToken(TransactionType.CONF))
                .when()
                .post(SCENARIOS_BASE_PATH)
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

    protected static String buildToken(TransactionType typeEnum) {
        return JWTUtils.buildTokenConnector(
                TokenToBuildDTO.builder()
                        .factor(CONNECTOR_FACTOR_TEST_DEFAULT)
                        .jwtId(JWT_ID)
                        .transactionId(Math.random())
                        .connectorId(CONNECTOR_ID_TEST_DEFAULT)
                        .environmentId(ENVIRONMENT_ID)
                        .type(String.valueOf(typeEnum))
                        .build())
                .getToken();
    }
}
