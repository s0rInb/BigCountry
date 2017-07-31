package com.gmail.s0rInb.entities.nis;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gmail.s0rInb.Utils.LocalDateDeserializer;
import com.gmail.s0rInb.Utils.LocalDateSerializer;
import com.gmail.s0rInb.entities.Consultation;
import com.gmail.s0rInb.entities.Patient;
import com.gmail.s0rInb.entities.nis.five.*;
import com.gmail.s0rInb.entities.nis.four.*;
import com.gmail.s0rInb.entities.nis.seven.Seven;
import com.gmail.s0rInb.entities.nis.seven.Seven_1;
import com.gmail.s0rInb.entities.nis.seven.Seven_2;
import com.gmail.s0rInb.entities.nis.seven.Seven_3;
import com.gmail.s0rInb.entities.nis.six.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@Transactional
public class AdverseEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "patient_id")
	private Long patientId;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate lethalDate;
	private String additionalText;


	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "a_id")
	private A a;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "b_id")
	private B b;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "c_id")
	private C c;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "d_id")
	private D d;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "one_id")
	private One one;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "two_id")
	private Two two;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "three_id")
	private Three three;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "four_a_id")
	private Four_a four_a;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "four_b_id")
	private Four_b four_b;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "four_c_id")
	private Four_c four_c;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "four_d_id")
	private Four_d four_d;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "five_a_id")
	private Five_a five_a;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "five_b_id")
	private Five_b five_b;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "five_c_id")
	private Five_c five_c;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "five_d_id")
	private Five_d five_d;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "five_e_id")
	private Five_e five_e;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "five_f_id")
	private Five_f five_f;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "six_1_id")
	private Six_1 six_1;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "six_2_id")
	private Six_2 six_2;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "six_3_id")
	private Six_3 six_3;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "six_4_id")
	private Six_4 six_4;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "six_5_id")
	private Six_5 six_5;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "seven_1_id")
	private Seven_1 seven_1;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "seven_2_id")
	private Seven_2 seven_2;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "seven_3_id")
	private Seven_3 seven_3;
}
