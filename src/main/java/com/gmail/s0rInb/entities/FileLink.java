package com.gmail.s0rInb.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "files")
@Audited
public class FileLink implements Serializable{
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@Size(max = 1024)
		@Column(name = "file_link")
		private String path;

		@Size(max = 256)
		@Column(name = "file_name")
		private String name;

		@Size(max = 256)
		@Column(name = "file_type")
		private String fileType;

		@Column(name = "patientId")
		private Long patientId;


	}
