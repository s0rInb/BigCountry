package com.gmail.s0rInb.entities.nis;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
public class One {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String surname;

	private String address;

	private String index;

	private String country;

	private String email;

	private String phone;

	private String fax;

	private String applicant;

	private String doctorText;

	private String anotherText;

	public void setDoctorText(String doctorText) {
		if(Objects.equals(this.getApplicant(), "doctor")) {
			this.doctorText = doctorText;
		} else{
			this.doctorText=null;
		}
	}

	public void setAnotherText(String anotherText) {
		if(Objects.equals(this.getApplicant(), "another")) {
			this.anotherText = anotherText;
		} else{
			this.anotherText=null;
		}
	}
	private String reg;


}
