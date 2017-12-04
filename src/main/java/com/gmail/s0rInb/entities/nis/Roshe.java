package com.gmail.s0rInb.entities.nis;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gmail.s0rInb.Utils.LocalDateDeserializer;
import com.gmail.s0rInb.Utils.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
public class Roshe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lrn;

    private String aer;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate securityDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    private String type;

    private String anotherText;

    private String nisId;

    private String mapId;

    private String cupaa;

    private String ptap;

    private String utility;

    private String additional;

    public void setAnotherText(String anotherText) {
        if(Objects.equals(this.getType(), "another")) {
            this.anotherText = anotherText;
        } else{
            this.anotherText=null;
        }
    }
}
