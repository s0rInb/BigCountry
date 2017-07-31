package com.gmail.s0rInb.entities.nis.four;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gmail.s0rInb.Utils.LocalDateDeserializer;
import com.gmail.s0rInb.Utils.LocalDateSerializer;
import com.gmail.s0rInb.entities.nis.AdverseEvent;
import com.gmail.s0rInb.entities.nis.AdverseEvent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "DISCRIMINATOR_COLUMN", discriminatorType = DiscriminatorType.STRING)
@MappedSuperclass
public abstract class Four {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String indications;

	private String dose;

	private String path;

	private String frequency;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate startDate;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate endDate;

	private String number;

	private String nis;

	private String again;

	private String phenomenon;

	private String again_nis;

}
