package com.gmail.s0rInb.controller;

import com.gmail.s0rInb.authentication.ScopeComponent;
import com.gmail.s0rInb.entities.RevisionInfo;
import com.gmail.s0rInb.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RevisionListener implements org.hibernate.envers.RevisionListener{

    @Override
    public void newRevision(Object revisionEntity) {
        RevisionInfo revisionInfo = (RevisionInfo) revisionEntity;
        revisionInfo.setUser(ScopeComponent.getCurrentUser());
    }
}
