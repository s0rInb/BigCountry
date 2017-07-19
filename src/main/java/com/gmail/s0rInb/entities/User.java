package com.gmail.s0rInb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name="users")
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 100)
	@NotNull
	@Column(name="first_name", nullable = false)
	private String firstName;

	@Size(max = 100)
	@NotNull
	@Column(name="last_name", nullable = false)
	private String lastName;

	@Size(max = 100)
	@NotNull
	@Column(name="username")
	private String username;

	@Size(max = 100)
	@NotNull
	@Column(name="password")
	private String password;

	@Column(nullable = false)
	@Size(max = 256)
	@JsonIgnore
	private String salt;

	@Column(nullable = false)
	@Size(max = 256)
	@JsonIgnore
	private String hash;
//
	@Column(name = "password_inputs")
	@JsonIgnore
	private Integer passwordInputs;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = {CascadeType.ALL})
	private Set<UserValue> userValues;
}