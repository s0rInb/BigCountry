package com.gmail.s0rInb.entities.nis.five;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("_b")
@Entity
@Getter
@Setter
public class Five_b extends Five{
}
