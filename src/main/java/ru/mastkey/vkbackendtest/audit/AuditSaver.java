package ru.mastkey.vkbackendtest.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mastkey.vkbackendtest.entity.Audit;
import ru.mastkey.vkbackendtest.repositories.AuditRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AuditSaver {
    private final AuditRepository auditRepository;
    public void save(String ip, String username, String endPoint, String method, boolean status) {
        Audit audit = new Audit();
        audit.setUser(username);
        audit.setIp(ip);
        audit.setTimestamp(LocalDateTime.now());
        audit.setEndpoint(endPoint);
        audit.setMethod(method);
        audit.setStatus(!status ? "DENIED" : "GRANTED");
        auditRepository.save(audit);
    }
}
