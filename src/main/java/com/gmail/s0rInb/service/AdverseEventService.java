package com.gmail.s0rInb.service;

import com.gmail.s0rInb.Utils.MailMail;
import com.gmail.s0rInb.entities.nis.*;
import com.gmail.s0rInb.entities.nis.five.Five;
import com.gmail.s0rInb.entities.nis.four.Four;
import com.gmail.s0rInb.entities.nis.seven.Seven;
import com.gmail.s0rInb.entities.nis.six.Six;
import com.gmail.s0rInb.repository.AdverseEventRepository;
import com.gmail.s0rInb.repository.PatientRepository;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

//import com.itextpdf.text.Document;

@Service("adverseEventService")
@Transactional
public class AdverseEventService {
	private final static String VIDEO_UPLOAD = System.getProperty("videoUploadDir");
	@Autowired
	AdverseEventRepository adverseEventRepository;
	@Autowired
	PatientService patientService;
	@Autowired
	MailMail mailMail;

	public AdverseEvent findById(Long id) {
		return adverseEventRepository.findOne(id);
	}

	public AdverseEvent save(AdverseEvent adverseEvent) {
		return adverseEventRepository.save(adverseEvent);
	}

	public List<HashMap> findHashMapList() {
		List<HashMap> hashMapList = adverseEventRepository.findHashMapList();
		hashMapList.removeIf(hashMap -> hashMap.get("pId")==null);
		hashMapList.forEach(hashMap -> hashMap.put("patientId",patientService.findById((Long)hashMap.get("pId")).getPatientId()));
		return hashMapList;
	}

	public void delete(AdverseEvent adverseEvent) {
		adverseEventRepository.delete(adverseEvent);
	}

	public void makePdf(AdverseEvent adverseEvent) throws IOException, DocumentException {
		ClassLoader classLoader = getClass().getClassLoader();
		PdfReader reader = new PdfReader(classLoader.getResource("nis_form.pdf"));
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(VIDEO_UPLOAD + "/adverseEvent/" + "AdverseEvent_" + adverseEvent.getId() + ".pdf"));
		BaseFont bf = BaseFont.createFont(classLoader.getResource("7454.ttf").getFile(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		AcroFields form = stamper.getAcroFields();
		form.addSubstitutionFont(bf);
		HashMap<String, String> hashMap = prepareAdverseEventToPdf(adverseEvent);
		hashMap.forEach((o, o2) -> {
			if (o.equals("a_nis") ||
					o.equals("a_exclude") ||
					o.equals("one_applicant") || o.equals("one_reg") ||
					o.equals("two_nis") ||
					o.equals("three_weight") || o.equals("three_sex") || o.equals("tree_height") || o.equals("three_age") || o.equals("three_ethos") ||
					o.equals("four_a_nis") || o.equals("four_b_nis") || o.equals("four_c_nis") || o.equals("four_d_nis") ||
					o.equals("four_a_again") || o.equals("four_b_again") || o.equals("four_c_again") || o.equals("four_d_again") ||
					o.equals("four_a_phenomenon") || o.equals("four_b_phenomenon") || o.equals("four_c_phenomenon") || o.equals("four_d_phenomenon") ||
					o.equals("four_a_again_nis") || o.equals("four_b_again_nis") || o.equals("four_c_again_nis") || o.equals("four_d_again_nis") ||
					o.equals("seven_1_inWork") || o.equals("seven_2_inWork") || o.equals("seven_3_inWork")) {
				try {
					form.setField(o + "_" + o2, "On");
				} catch (IOException | DocumentException e) {
					e.printStackTrace();
				}
			} else {
				try {
					form.setField(o, o2);
				} catch (IOException | DocumentException e) {
					e.printStackTrace();
				}
			}

		});
		stamper.setFormFlattening(true);
		stamper.close();
		reader.close();
		mailMail.sendMail("test", "test", VIDEO_UPLOAD + "/adverseEvent/" + "AdverseEvent_" + adverseEvent.getId() + ".pdf");
	}


	private HashMap prepareAdverseEventToPdf(AdverseEvent adverseEvent) {
		HashMap result = new HashMap();
		result.put("additional_text", adverseEvent.getAdditionalText());
		if (adverseEvent.getLethalDate() != null)
			result.put("lethalDate", adverseEvent.getLethalDate().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")));
		if (adverseEvent.getA() != null) {
			String s = "a_";
			A a = adverseEvent.getA();
			result.put(s + "centerId", a.getCenterId());
			if (a.getExclude() != null) result.put(s + "exclude", a.getExclude());
			if (a.getNis() != null) result.put(s + "nis", a.getNis());
			result.put(s + "nisId", a.getNisId());
			result.put(s + "patientId", a.getPatientId());
		}
		if (adverseEvent.getB() != null) {
			String s = "b_";
			B b = adverseEvent.getB();
			result.put(s + "programId", b.getProgramId());
			result.put(s + "projectName", b.getProjectName());
			result.put(s + "agentName", b.getAgentName());
			result.put(s + "applicantId", b.getApplicationId());
			result.put(s + "address", b.getAddress());
			result.put(s + "email", b.getEmail());
			result.put(s + "country", b.getCountry());
			result.put(s + "phone", b.getPhone());
			result.put(s + "fax", b.getFax());
		}
		if (adverseEvent.getC() != null) {
			String s = "c_";
			C c = adverseEvent.getC();
			result.put(s + "patientId", c.getPatientId());
			result.put(s + "programId", c.getProgramId());
		}
		if (adverseEvent.getD() != null) {
			String s = "d_";
			D d = adverseEvent.getD();
			result.put(s + "patientId", d.getPatientId());
			result.put(s + "programId", d.getProgramId());
		}
		if (adverseEvent.getOne() != null) {
			String s = "one_";
			One one = adverseEvent.getOne();
			result.put(s + "name", one.getName());
			result.put(s + "surname", one.getSurname());
			result.put(s + "address", one.getAddress());
			result.put(s + "index", one.getIndex());
			result.put(s + "contry", one.getCountry());
			result.put(s + "email", one.getEmail());
			result.put(s + "phone", one.getPhone());
			result.put(s + "fax", one.getFax());
			result.put(s + "applicant", one.getApplicant());
			result.put(s + "doctorText", one.getDoctorText());
			result.put(s + "anotherText", one.getAnotherText());
			if (one.getReg() != null) result.put(s + "reg", one.getReg());
		}
		if (adverseEvent.getTwo() != null) {
			String s = "two_";
			Two two = adverseEvent.getTwo();
			result.put(s + "doctorContacts", two.getDoctorContacts());
			if (two.getNis() != null) result.put(s + "nis", two.getNis());
		}
		if (adverseEvent.getThree() != null) {
			String s = "three_";
			Three three = adverseEvent.getThree();
			result.put(s + "name", three.getName());
			result.put(s + "sex", three.getSex());
			result.put(s + "age", three.getAge());
			result.put(s + "birthDate", three.getBirthDate());
			result.put(s + "weight", "kg");
			result.put(s + "weightText", three.getWeightText());
			result.put(s + "height", "sm");
			result.put(s + "heightText", three.getHeightText());
			result.put(s + "ethos", three.getEthos());
			result.put(s + "anotherText", three.getAnotherText());
		}
		if (adverseEvent.getFour_a() != null) result.putAll(fourToHashMap(adverseEvent.getFour_a(), "four_a_"));
		if (adverseEvent.getFour_b() != null) result.putAll(fourToHashMap(adverseEvent.getFour_b(), "four_b_"));
		if (adverseEvent.getFour_c() != null) result.putAll(fourToHashMap(adverseEvent.getFour_c(), "four_c_"));
		if (adverseEvent.getFour_d() != null) result.putAll(fourToHashMap(adverseEvent.getFour_d(), "four_d_"));

		if (adverseEvent.getFive_a() != null) result.putAll(fiveToHashMap(adverseEvent.getFive_a(), "five_a_"));
		if (adverseEvent.getFive_b() != null) result.putAll(fiveToHashMap(adverseEvent.getFive_b(), "five_b_"));
		if (adverseEvent.getFive_c() != null) result.putAll(fiveToHashMap(adverseEvent.getFive_c(), "five_c_"));
		if (adverseEvent.getFive_d() != null) result.putAll(fiveToHashMap(adverseEvent.getFive_d(), "five_d_"));
		if (adverseEvent.getFive_e() != null) result.putAll(fiveToHashMap(adverseEvent.getFive_e(), "five_e_"));
		if (adverseEvent.getFive_f() != null) result.putAll(fiveToHashMap(adverseEvent.getFive_f(), "five_f_"));

		if (adverseEvent.getSix_1() != null) result.putAll(sixToHashMap(adverseEvent.getSix_1(), "six_1_"));
		if (adverseEvent.getSix_2() != null) result.putAll(sixToHashMap(adverseEvent.getSix_2(), "six_2_"));
		if (adverseEvent.getSix_3() != null) result.putAll(sixToHashMap(adverseEvent.getSix_3(), "six_3_"));
		if (adverseEvent.getSix_4() != null) result.putAll(sixToHashMap(adverseEvent.getSix_4(), "six_4_"));
		if (adverseEvent.getSix_5() != null) result.putAll(sixToHashMap(adverseEvent.getSix_5(), "six_5_"));

		if (adverseEvent.getSeven_1() != null) result.putAll(sevenToHashMap(adverseEvent.getSeven_1(), "seven_1_"));
		if (adverseEvent.getSeven_2() != null) result.putAll(sevenToHashMap(adverseEvent.getSeven_2(), "seven_2_"));
		if (adverseEvent.getSeven_3() != null) result.putAll(sevenToHashMap(adverseEvent.getSeven_3(), "seven_3_"));
		return result;
	}

	private HashMap fourToHashMap(Four four, String s) {
		HashMap result = new HashMap();
		result.put(s + "name", four.getName());
		result.put(s + "indications", four.getIndications());
		result.put(s + "dose", four.getDose());
		result.put(s + "path", four.getPath());
		result.put(s + "frequency", four.getFrequency());
		if (four.getStartDate() != null)
			result.put(s + "startDate", four.getStartDate().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")));
		if (four.getEndDate() != null)
			result.put(s + "endDate", four.getEndDate().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")));
		result.put(s + "number", four.getNumber());
		if (four.getNis() != null) result.put(s + "nis", four.getNis());
		if (four.getAgain() != null) result.put(s + "again", four.getAgain());
		if (four.getPhenomenon() != null) result.put(s + "phenomenon", four.getPhenomenon());
		if (four.getAgain_nis() != null) result.put(s + "again_nis", four.getAgain_nis());
		return result;
	}

	private HashMap fiveToHashMap(Five five, String s) {
		HashMap result = new HashMap();
		result.put(s + "nis", five.getNis());
		if (five.getStartDate() != null)
			result.put(s + "startDate", five.getStartDate().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")));
		if (five.getResult() != null) result.put(s + "result", five.getResult().toString());
		if (five.getEndDate() != null)
			result.put(s + "endDate", five.getEndDate().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")));
		if (five.getHard() != null) result.put(s + "hard", five.getHard().toString());
		result.put(s + "a", five.getA());
		result.put(s + "b", five.getB());
		result.put(s + "c", five.getC());
		result.put(s + "d", five.getD());
		return result;
	}

	private HashMap sixToHashMap(Six six, String s) {
		HashMap result = new HashMap();
		result.put(s + "drug", six.getDrug());
		result.put(s + "show", six.getShow());
		result.put(s + "dose", six.getDose());
		result.put(s + "path", six.getPath());
		result.put(s + "frequency", six.getFrequency());
		if (six.getStartDate() != null)
			result.put(s + "startDate", six.getStartDate().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")));
		if (six.getEndDate() != null)
			result.put(s + "endDate", six.getEndDate().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")));
		return result;
	}

	private HashMap sevenToHashMap(Seven seven, String s) {
		HashMap result = new HashMap();
		result.put(s + "analyses", seven.getAnalyses());
		result.put(s + "result", seven.getResult());
		result.put(s + "value", seven.getValue());
		if (seven.getInWork()) result.put(s + "inWork", "");
		if (seven.getDate() != null)
			result.put(s + "date", seven.getDate().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")));
		return result;
	}
}



