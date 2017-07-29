package com.gmail.s0rInb.entities.nis.five;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("_d")
@Entity
@Getter
@Setter
public class Five_d extends Five{
}
