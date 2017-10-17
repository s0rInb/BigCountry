package com.gmail.s0rInb.entities.dictionary;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="who_call")
@Audited
public class WhoCall extends Dictionary {
}
