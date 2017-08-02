package com.gmail.s0rInb.entities.nis;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class C {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	private String programId;

	private String patientId;
}
