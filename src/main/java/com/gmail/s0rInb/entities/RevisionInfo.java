package com.gmail.s0rInb.entities;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@RevisionEntity(com.gmail.s0rInb.controller.RevisionListener.class)
@Table(name = "revinfo")
public class RevisionInfo extends DefaultRevisionEntity {
    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
