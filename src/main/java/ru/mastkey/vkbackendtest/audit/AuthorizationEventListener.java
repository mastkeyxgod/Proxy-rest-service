package ru.mastkey.vkbackendtest.audit;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.event.AuthorizationEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorizationEventListener {

    private final AuditSaver auditSaver;

    @Autowired
    private HttpServletRequest request;


    @EventListener
    public void auditEventHappened(AuthorizationEvent authorizationEvent) {
        AuthorizationDecision decision = authorizationEvent.getAuthorizationDecision();
        String name = authorizationEvent.getAuthentication().get().getName();

        if (!request.getRequestURI().equals("/error")) {
            auditSaver.save(request.getRemoteAddr(), name, request.getRequestURI(),
                    request.getMethod(), decision.isGranted());
        }
    }
}