package $package .model;

import com.sensedia.connector_core.annotation.ConfigProperty;

public class Property {

    @ConfigProperty(defaultValue = "localhost")
    private String host;

    @ConfigProperty(defaultValue = "8080")
    private String port;

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }
}