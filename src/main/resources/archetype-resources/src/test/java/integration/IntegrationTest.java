package $package .integration;

import $package .BaseIntegrationTest;
import com.sensedia.apimicrogateway.constants.Constants;
import com.sensedia.commonsconnector.dto.ConnectorRequestDTO;
import com.sensedia.commonsconnector.dto.ObjectKeyValue;
import com.sensedia.commonsconnector.enums.TransactionType;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;

import javax.ws.rs.core.Response;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static io.undertow.util.Headers.AUTHORIZATION_STRING;
import static org.hamcrest.core.IsEqual.equalTo;

@QuarkusTest
public class IntegrationTest extends BaseIntegrationTest {

//    @Test
    public void validateRequestSuccess() {
        ConnectorRequestDTO connectorRequestDTO = ConnectorRequestDTO.builder()
                .instruction("{\"name\": \"$body.name\", \"lastName\": \"$headerParam.lastName\"}")
                .headers(Collections.singletonList(ObjectKeyValue.builder().key("lastName").value("Adams").build()))
                .content("{\"name\": \"John\"}")
                .checksum(CHECKSUM_DEFAULT)
                .build();

        buildTestRequest(connectorRequestDTO)
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(equalTo("{\"NAME\": \"JOHN\", \"LASTNAME\": \"ADAMS\"}"));
    }

    private io.restassured.response.Response buildTestRequest(ConnectorRequestDTO connectorRequestDTO) {
        return given()
                .header(AUTHORIZATION_STRING, buildToken(TransactionType.RUNTIME))
                .body(connectorRequestDTO)
                .contentType(ContentType.JSON)
                .when()
                .post(Constants.APPLICATION_PATH.concat("/executions"));
    }

}
