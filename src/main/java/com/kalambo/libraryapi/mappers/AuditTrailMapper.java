package com.kalambo.libraryapi.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.AuditTrail;
import com.kalambo.libraryapi.responses.IAuditTrail;
import com.kalambo.libraryapi.responses.IAuditTrailV2;
import com.kalambo.libraryapi.responses.IAuditTrailV3;

@Component
public class AuditTrailMapper {
    @Autowired
    private UserMapper userMapper;

    public IAuditTrail map(AuditTrail auditTrail) {
        IAuditTrail response = new IAuditTrail()
                .setUser(userMapper.mapToV3(auditTrail.getUser()))
                .setId(auditTrail.getId()).setCreatedAt(auditTrail.getCreatedAt())
                .setAction(auditTrail.getAction()).setResourceName(auditTrail.getResourceName())
                .setResourceId(auditTrail.getResourceId()).setPreviousValues(auditTrail.getPreviousValues())
                .setUpdatedValues(auditTrail.getUpdatedValues());

        return response;
    }

    public IAuditTrailV2 mapToV2(AuditTrail auditTrail) {
        IAuditTrailV2 response = new IAuditTrailV2()
                .setUser(userMapper.mapToV3(auditTrail.getUser()))
                .setId(auditTrail.getId()).setCreatedAt(auditTrail.getCreatedAt())
                .setAction(auditTrail.getAction()).setResourceName(auditTrail.getResourceName());

        return response;
    }

    public IAuditTrailV3 mapToV3(AuditTrail auditTrail) {
        return new IAuditTrailV3()
                .setUser(userMapper.mapToV3(auditTrail.getUser())).setId(auditTrail.getId());
    }
}
