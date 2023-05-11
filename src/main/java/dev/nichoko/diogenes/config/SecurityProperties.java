package dev.nichoko.diogenes.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private boolean disableSecurity;

    public void setDisableSecurity(boolean disableSecurity) {
        this.disableSecurity = disableSecurity;
    }

    public boolean isDisableSecurity() {
        return disableSecurity;
    }   
}
