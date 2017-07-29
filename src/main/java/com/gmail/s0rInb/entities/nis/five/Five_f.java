package com.gmail.s0rInb.entities.nis.five;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("_f")
@Entity
@Getter
@Setter
public class Five_f extends Five{
}
