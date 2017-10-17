package com.gmail.s0rInb.entities.dictionary;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "who_sent_to_cons")
@Entity
@Audited
public class WhoSentToConsultation extends Dictionary {
}
