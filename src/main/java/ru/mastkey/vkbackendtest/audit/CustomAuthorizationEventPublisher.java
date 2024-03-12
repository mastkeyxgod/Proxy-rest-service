package ru.mastkey.vkbackendtest.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.security.authorization.event.AuthorizationGrantedEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationEventPublisher implements AuthorizationEventPublisher {

    private final ApplicationEventPublisher publisher;

    @Override
    public <T> void publishAuthorizationEvent(Supplier<Authentication> authentication,
                                              T object, AuthorizationDecision decision) {
        if (decision == null) {
            return;
        }

        if (decision.isGranted()) {
            AuthorizationGrantedEvent<T> granted = new AuthorizationGrantedEvent<>(authentication, object, decision);
            publisher.publishEvent(granted);
            return;
        }

        AuthorizationDeniedEvent<T> failure = new AuthorizationDeniedEvent<>(authentication, object, decision);
        publisher.publishEvent(failure);
    }

}
