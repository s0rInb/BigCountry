package com.gmail.s0rInb.entities.nis;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class Three {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "par_nis_id")
	private Nis parNis;

	private String name;

	private String sex;

	private String age;

	private String birthDate;

	private String weight;

	private String weightText;

	private String height;

	private String heightText;

	private String ethos;

	private String anotherText;
}
