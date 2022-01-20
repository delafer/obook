package net.j7.ebook.config;

//@Configuration
public class WebsocketSecurityConfiguration /*extends AbstractSecurityWebSocketMessageBrokerConfigurer*/ {

    protected boolean sameOriginDisabled() {
        return false;
    }
}
