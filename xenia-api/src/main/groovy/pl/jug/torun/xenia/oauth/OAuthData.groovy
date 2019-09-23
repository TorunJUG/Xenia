package pl.jug.torun.xenia.oauth

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import java.awt.Desktop

@Component
@Configuration
@ConfigurationProperties(prefix = "oauth")
class OAuthData {
    String requestToken
    String refreshToken
    int expiryPeriod

    String clientId
    String clientSecret

    @PostConstruct
    void init() {
        String address = "https://secure.meetup.com/oauth2/authorize" +
                "?client_id="+ clientId +
                "&response_type=code" +
                "&redirect_uri=http://localhost:8080/oauth2/redirect";

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI(address))
        } else {
            println "Browse action not supported, please navigate manually to the following address:"
            println address
        }
    }

    void fromResponse(response) {
        refreshToken = response["refresh_token"]
        expiryPeriod = response["expires_in"] as Integer
        requestToken = response["access_token"]
    }
}
