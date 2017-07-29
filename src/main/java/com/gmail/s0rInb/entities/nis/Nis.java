package com.gmail.s0rInb.entities.nis;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gmail.s0rInb.Utils.LocalDateDeserializer;
import com.gmail.s0rInb.Utils.LocalDateSerializer;
import com.gmail.s0rInb.entities.nis.five.Five;
import com.gmail.s0rInb.entities.nis.four.Four;
import com.gmail.s0rInb.entities.nis.seven.Seven;
import com.gmail.s0rInb.entities.nis.six.Six;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Nis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate lethalDate;

	private String additionalText;

	@OneToOne(mappedBy = "parNis", cascade = CascadeType.ALL, orphanRemoval = true)
	private A a;

	@OneToOne(mappedBy = "parNis", cascade = CascadeType.ALL, orphanRemoval = true)
	private B b;

	@OneToOne(mappedBy = "parNis", cascade = CascadeType.ALL, orphanRemoval = true)
	private C c;

	@OneToOne(mappedBy = "parNis", cascade = CascadeType.ALL, orphanRemoval = true)
	private D d;

	@OneToOne(mappedBy = "parNis", cascade = CascadeType.ALL, orphanRemoval = true)
	private One one;

	@OneToOne(mappedBy = "parNis", cascade = CascadeType.ALL, orphanRemoval = true)
	private Two two;

	@OneToOne(mappedBy = "parNis", cascade = CascadeType.ALL, orphanRemoval = true)
	private Three three;

	@OneToMany(mappedBy = "parNis", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Four> four;


	@OneToMany(mappedBy = "parNis", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Five> five;


	@OneToMany(mappedBy = "parNis", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Six> six;


	@OneToMany(mappedBy = "parNis", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Seven> seven;

}
