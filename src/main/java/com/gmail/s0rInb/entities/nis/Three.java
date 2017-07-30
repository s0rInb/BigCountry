package com.gmail.s0rInb.entities.nis;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
public class Three {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String sex;

	private String age;

	private String birthDate;

	private String weight; //ignored

	private String weightText;

	private String height; //ignored

	private String heightText;

	private String ethos;

	private String anotherText;

	public void setAnotherText(String anotherText) {
		if(Objects.equals(this.getEthos(), "another")) {
			this.anotherText = anotherText;
		} else{
			this.anotherText=null;
		}
	}
}
