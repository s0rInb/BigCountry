package com.gmail.s0rInb.entities.dictionary;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by s0rInb on 12.07.2017.
 */
@Entity
@Table(name="expert_center")
@Audited
public class ExpertCenter  extends Dictionary{
}
