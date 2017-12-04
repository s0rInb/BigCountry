package com.gmail.s0rInb.entities.nis;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class Nine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String a;

    private String b;

    private String c;

    private String one;

    private String two;

    private String three;

    private String d;

    private String e;

    private String f;

    private String g;
}
