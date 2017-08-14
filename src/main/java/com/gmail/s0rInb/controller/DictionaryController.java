package com.gmail.s0rInb.controller;

import com.gmail.s0rInb.entities.dictionary.Dictionary;
import com.gmail.s0rInb.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DictionaryController {


	@Autowired
	private DictionaryService dictionaryService;

	@RequestMapping(method = RequestMethod.GET, value = "subjectRF/{id}", produces = "application/json")
	@ResponseBody
	public Response getSubjectRF(@PathVariable("id") Long id) {
		return getResponseById("SubjectRF", id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "subjectRF", produces = "application/json")
	@ResponseBody
	public Response getSubjectRF(@RequestParam(value = "q", required = false) String q) {
		return getResponseByQ("SubjectRF", q);
	}
	@RequestMapping(method = RequestMethod.GET, value = "diagnosis/{id}", produces = "application/json")
	@ResponseBody
	public Response getDiagnosis(@PathVariable("id") Long id) {
		return getResponseById("Diagnosis", id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "diagnosis", produces = "application/json")
	@ResponseBody
	public Response getDiagnosis(@RequestParam(value = "q", required = false) String q) {
		return getResponseByQ("Diagnosis", q);
	}
	@RequestMapping(method = RequestMethod.GET, value = "whoCall/{id}", produces = "application/json")
	@ResponseBody
	public Response getWhoCall(@PathVariable("id") Long id) {
		return getResponseById("WhoCall", id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "whoCall", produces = "application/json")
	@ResponseBody
	public Response getWhoCall(@RequestParam(value = "q", required = false) String q) {
		return getResponseByQ("WhoCall", q);
	}

	@RequestMapping(method = RequestMethod.GET, value = "expertCenter", produces = "application/json")
	@ResponseBody
	public Response getExpertCenter(@RequestParam(value = "q", required = false) String q) {
		return getResponseByQ("ExpertCenter", q);
	}
	@RequestMapping(method = RequestMethod.GET, value = "expertCenter/{id}", produces = "application/json")
	@ResponseBody
	public Response getExpertCenter(@PathVariable("id") Long id) {
		return getResponseById("ExpertCenter", id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "whoSentToCons", produces = "application/json")
	@ResponseBody
	public Response getWhoSentToConsultation(@RequestParam(value = "q", required = false) String q) {
		return getResponseByQ("WhoSentToConsultation", q);
	}
	@RequestMapping(method = RequestMethod.GET, value = "whoSentToCons/{id}", produces = "application/json")
	@ResponseBody
	public Response getWhoSentToConsultation(@PathVariable("id") Long id) {
		return getResponseById("WhoSentToConsultation", id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "whoLegalSupport", produces = "application/json")
	@ResponseBody
	public Response getWhoLegalSupport(@RequestParam(value = "q", required = false) String q) {
		return getResponseByQ("WhoLegalSupport", q);
	}
	@RequestMapping(method = RequestMethod.GET, value = "whoLegalSupport/{id}", produces = "application/json")
	@ResponseBody
	public Response getWhoLegalSupport(@PathVariable("id") Long id) {
		return getResponseById("WhoLegalSupport", id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "legalSupportResult", produces = "application/json")
	@ResponseBody
	public Response getLegalSupportResult(@RequestParam(value = "q", required = false) String q) {
		return getResponseByQ("LegalSupportResult", q);
	}
	@RequestMapping(method = RequestMethod.GET, value = "legalSupportResult/{id}", produces = "application/json")
	@ResponseBody
	public Response getLegalSupportResult(@PathVariable("id") Long id) {
		return getResponseById("LegalSupportResult", id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "consultationType", produces = "application/json")
	@ResponseBody
	public Response getConsultationType(@RequestParam(value = "q", required = false) String q) {
		return getResponseByQ("ConsultationType", q);
	}
	@RequestMapping(method = RequestMethod.GET, value = "consultationType/{id}", produces = "application/json")
	@ResponseBody
	public Response getConsultationType(@PathVariable("id") Long id) {
		return getResponseById("ConsultationType", id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "consultationPatientStatus", produces = "application/json")
	@ResponseBody
	public Response getConsultationPatientStatus(@RequestParam(value = "q", required = false) String q) {
		return getResponseByQ("ConsultationPatientStatus", q);
	}
	@RequestMapping(method = RequestMethod.GET, value = "consultationPatientStatus/{id}", produces = "application/json")
	@ResponseBody
	public Response getConsultationPatientStatus(@PathVariable("id") Long id) {
		return getResponseById("ConsultationPatientStatus", id);
	}
	@RequestMapping(method = RequestMethod.GET, value = "legalSupportPatientStatus", produces = "application/json")
	@ResponseBody
	public Response getLegalSupportPatientStatus(@RequestParam(value = "q", required = false) String q) {
		return getResponseByQ("LegalSupportPatientStatus", q);
	}
	@RequestMapping(method = RequestMethod.GET, value = "legalSupportPatientStatus/{id}", produces = "application/json")
	@ResponseBody
	public Response getLegalSupportPatientStatus(@PathVariable("id") Long id) {
		return getResponseById("LegalSupportPatientStatus", id);
	}


	private Response getResponseByQ(String entityName, String q) {
		return getResponseByQ(entityName, q, null,null);
	}

	private Response getResponseById(String entityName, Long id){
		Dictionary dictionary = dictionaryService.findById(entityName, id);
		//UserValue userValues = userValueService.findByUserIdAndUserValueName(getUser().getId(), "locale");
		//String userValue = userValues.getValue();//TODO remove index
//		if (userValue.equals("en")){
//			dictionary.setName(translateString(dictionary.getName()));
//		}
		Response response = new Response();
		response.setEntity(dictionary);
		char c[] = entityName.toCharArray();
		c[0] = Character.toLowerCase(c[0]);
		entityName = new String(c);
		response.setEntityClass(entityName);
		return response;
	}
	private Response getResponseByQ(String entityName, String q, List<Long> excludedIds, List<Long> includeOnlyIds) {
		//UserValue userValues = getUserValueService().findByUserIdAndUserValueName(getUser().getId(), "locale");
		//String userValue = userValues.getValue();//TODO remove index
		if (q == null) {
			q = "";
		}
		q = q.toLowerCase();
		List<Dictionary> dictionaryList = dictionaryService.search(entityName, q);

//		if ((dictionaryList != null) && !dictionaryList.isEmpty() &&
//				((excludedIds != null) && !excludedIds.isEmpty() ||
//						(includeOnlyIds != null) && !includeOnlyIds.isEmpty())) {
//			List<Dictionary> excludedList = new ArrayList<>(dictionaryList.size());
//			dictionaryList.forEach((item) -> {
//				if ((item != null) && excludedIds != null && excludedIds.contains(item.getId()))
//					excludedList.add(item);
//				if ((item != null) && includeOnlyIds != null && !includeOnlyIds.contains(item.getId()))
//					excludedList.add(item);
//			});
//			dictionaryList.removeAll(excludedList);
//		}

//		if (userValue.equals("en")) {
//			dictionaryList = translateDictionary(dictionaryList);
//			if (enQ != null && !enQ.equals("")) {
//				dictionaryList = filterDictionary(dictionaryList, enQ);
//			}
//		}
//
//		logger.info("Listing Types " + q);
		Response response = new Response();
		response.setData(dictionaryList);
		return response;
	}
}
