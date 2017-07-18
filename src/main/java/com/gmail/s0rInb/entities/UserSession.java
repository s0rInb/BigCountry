package com.gmail.s0rInb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "USER_SESSION")
@Getter
@Setter
public class UserSession{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	@JsonIgnore
	private String token;

	@OneToOne
	@JoinColumn(name = "USER_ID")
	@NotNull
	private User user;

	@Column(name = "LAST_LOGIN")
	@JsonIgnore
	private Date lastLogin;
}
