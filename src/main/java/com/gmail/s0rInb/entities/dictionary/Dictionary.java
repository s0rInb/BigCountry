package com.gmail.s0rInb.entities.dictionary;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by s0rInb on 12.07.2017.
 */
@MappedSuperclass
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Size(max = 320)
    @NotNull
    @Column(name="name", nullable = false)
    @Getter
    @Setter
    private String name;
}
