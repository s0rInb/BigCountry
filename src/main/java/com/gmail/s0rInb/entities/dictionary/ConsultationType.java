package com.gmail.s0rInb.entities.dictionary;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "consultation_type")
@Entity
@Audited
public class ConsultationType extends Dictionary {

}
