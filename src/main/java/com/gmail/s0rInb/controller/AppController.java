package com.gmail.s0rInb.controller;

import com.gmail.s0rInb.DTO.PatientCommentDTO;
import com.gmail.s0rInb.DTO.PatientDTO;
import com.gmail.s0rInb.authentication.LoginController;
import com.gmail.s0rInb.authentication.ScopeComponent;
import com.gmail.s0rInb.entities.*;
import com.gmail.s0rInb.entities.dictionary.ConsultationType;
import com.gmail.s0rInb.entities.dictionary.Dictionary;
import com.gmail.s0rInb.entities.nis.AdverseEvent;
import com.gmail.s0rInb.repository.FileRepository;
import com.gmail.s0rInb.service.*;
import com.itextpdf.text.DocumentException;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.internal.util.io.StreamCopier.BUFFER_SIZE;

@Controller
public class AppController extends BaseController {
	private final static String VIDEO_UPLOAD = System.getProperty("videoUploadDir");
	private static final Logger logger = LogManager.getLogger(AppController.class);
	@Autowired
	UserService userService;
	@Autowired
	PatientService patientService;
	@Autowired
	FileRepository fileRepository;
	@Autowired
	AdverseEventService adverseEventService;
	@Autowired
	DictionaryService dictionaryService;

	@Autowired
	DoctorExpertCenterService doctorExpertCenterService;

	@Autowired
	PatientCommentsService patientCommentsService;
	@Override
	protected void init() {

	}

	@RequestMapping(method = RequestMethod.GET, value = "/patients", produces = "application/json")
	@ResponseBody
	public Response getPatients(HttpServletRequest request)
			throws IOException {
		logger.info("getPatients");
		List<PatientDTO> result = patientService.findAll().stream().map(o -> new PatientDTO(o,getUser().getUserRole())).collect(Collectors.toList());
//		result.sort(Comparator.comparing(PatientDTO::getId));
		Response response = new Response();
		response.setData(result);
		response.setUserRole(getUser().getUserRole().name());
		return response;
	}
    @RequestMapping(method = RequestMethod.GET, value = "/notReadPatient", produces = "application/json")
    @ResponseBody
    public Response getNotReadListPatientIdByUserId(HttpServletRequest request)
            throws IOException {
        List<Long> result = patientCommentsService.getNotReadListPatientIdByUserId(getUser().getId());
        Response response = new Response();
        response.setData(result);
        response.setUserRole(getUser().getUserRole().name());
        return response;
    }
	@RequestMapping(method = RequestMethod.GET, value = "/patient/{id}", produces = "application/json")
	@ResponseBody
	public Response getPatient(@PathVariable("id") Long id) {
		logger.info("getPatient");
		PatientDTO result = new PatientDTO(patientService.findById(id), getUser().getUserRole());
		Response response = new Response();
		response.setEntity(result);
		response.setUserRole(getUser().getUserRole().name());
		response.setEntityClass("patient");
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/patient", produces = "application/json")
	@ResponseBody
	public Response getPatientNull() {
		logger.info("getPatientNull");
		Patient result = patientService.findById(0L);
		Response response = new Response();
		response.setEntity(result);
		response.setUserRole(getUser().getUserRole().name());
		response.setEntityClass("patient");
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "patientUpdate", produces = "application/json")
	@ResponseBody
	public Response updatePatient(@RequestBody Patient patient) {
		logger.info("updatePatient");
		Response response = new Response();
		if (getUser().getUserRole().equals(UserRole.MANAGER)) patient = patientService.save(patient);
		response.setEntity(patient);
		response.setUserRole(getUser().getUserRole().name());
		response.setEntityClass("patient");
		return response;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/patientComments/{patientId}", produces = "application/json")
	@ResponseBody
	public Response getPatientsComments(@PathVariable("patientId") Long patientId) {
		logger.info("getPatientComments with patientId="+patientId);
		Response response = new Response();
		if (getUser().getUserRole().equals(UserRole.MANAGER)) {
            response.setData(patientCommentsService.findAllByPatientId(patientId).stream()
                    .map(PatientCommentDTO::new).sorted(Comparator.comparing(PatientCommentDTO::getId)).collect(Collectors.toList()));
        }
        patientCommentsService.deleteNotReadByPatientIdAndCurrentUserId(patientId);
        response.setEntityClass("patientComment");
		return response;
	}

    @RequestMapping(method = RequestMethod.POST, value = "patientCommentCreate/{patientId}", produces = "application/json")
    @ResponseBody
    public Response createPatientComment(@PathVariable("patientId") Long patientId,
                                         @RequestBody PatientComment patientComment) {
        logger.info("createPatientComment");
        Response response = new Response();
        if (getUser().getUserRole().equals(UserRole.MANAGER)) patientComment= patientCommentsService.createPatientComment(patientComment,patientId);
        response.setEntity(patientComment);
        response.setUserRole(getUser().getUserRole().name());
        response.setEntityClass("patient");
        return response;

    }
	@RequestMapping(method = RequestMethod.GET, value = "patientDelete/{id}", produces = "application/json")
	@ResponseBody
	public HttpStatus deletePatient(@PathVariable("id") Long id) {
		logger.info("deletePatient");
		if (getUser().getUserRole().equals(UserRole.MANAGER)) patientService.delete(patientService.findById(id));
		return HttpStatus.OK;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/fileLinks/{id}", produces = "application/json")
	@ResponseBody
	public Response getFileLinks(@PathVariable("id") Long id) {
		logger.info("getFileLinks");
		List<FileLink> result = fileRepository.findAllByPatientId(id);
		Response response = new Response();
		response.setEntity(result);
		response.setEntityClass("fileLinks");
		response.setUserRole(getUser().getUserRole().name());
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/deleteFileLinks/{id}", produces = "application/json")
	@ResponseBody
	public Response deleteFileLinks(@PathVariable("id") Long id) throws IOException {
		logger.info("deleteFileLinks");
		FileLink one = fileRepository.findOne(id);
		if (getUser().getUserRole().equals(UserRole.MANAGER)) {
			fileRepository.delete(id);
			Files.delete(Paths.get(one.getPath()));
		}
		Response response = new Response();
		response.setUserRole(getUser().getUserRole().name());
		response.setEntityClass("fileLinks");
		return response;
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, consumes = {"multipart/form-data"}, produces = "application/json")
	@ResponseBody
	public HttpStatus uploadFile(@RequestParam("patientId") Long patientId,
								 @RequestParam("fileType") String fileType,
								 @RequestParam("name") String name,
								 @RequestPart("file") MultipartFile file) {
		if (getUser().getUserRole().equals(UserRole.MANAGER)) {
			logger.info("uploadFile");
			if (file.isEmpty()) {
				logger.info("file is empty: HttpStatus.NO_CONTENT");
				return HttpStatus.NO_CONTENT;
			}

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
			String fileName = patientId + fileType + LocalDateTime.now().format(formatter) + "-" + name;
			logger.info("file name: " + fileName);
			Path path = null;
			FileOutputStream fos = null;
			try {
				final Path pathVideo = Paths.get(VIDEO_UPLOAD);
				if (!Files.exists(pathVideo)) {
					Files.createDirectory(pathVideo);
				}
				path = Paths.get(pathVideo.toString(), fileName);
				java.io.File uploadFile = new java.io.File(path.toString());

				fos = new FileOutputStream(uploadFile);

				logger.info("video path: " + path);
				IOUtils.copy(file.getInputStream(), fos);
			} catch (IOException e) {
				logger.error("Error while write file: ", e);
				logger.error("HttpStatus.BAD_REQUEST");
				return HttpStatus.BAD_REQUEST;
			}
			FileLink fileLink = new FileLink();
			fileLink.setName(fileName);
			fileLink.setPath(Paths.get(Paths.get(VIDEO_UPLOAD).toString(), fileName).toString());
			fileLink.setFileType(fileType);
			fileLink.setPatientId(patientId);
			fileRepository.save(fileLink);
		}
		return HttpStatus.OK;
	}

	@RequestMapping(value = "getFile", method = RequestMethod.GET)
	public void getFile(@RequestParam("id") Long id, HttpServletResponse response, HttpServletRequest request) throws IOException {
		logger.info("getFile");
			FileLink fileLink = fileRepository.findOne(id);
		if (getUser().getUserRole().equals(UserRole.MANAGER) ||
				(getUser().getUserRole().equals(UserRole.CUSTOMER) && fileLink.getFileType().equals("customer"))) {
			FileSystemResource fileSystemResource = new FileSystemResource(fileLink.getPath());
			File downloadFile = new File(fileSystemResource.getPath());
			FileInputStream inputStream = new FileInputStream(downloadFile);

			// get MIME type of the file
			String mimeType = "application/octet-stream";

			System.out.println("MIME type: " + mimeType);

			// set content attributes for the response
			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());

			// set headers for the response
			String headerKey = "Content-Disposition";
			String fileName = new String(downloadFile.getName().getBytes("Cp1251"),"Cp1252");
			String headerValue = "attachment; filename="+ fileName;
			response.setHeader(headerKey, headerValue);

			// get output stream of the response
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			// write bytes read from the input stream into the output stream
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/adverseEvent/{id}", produces = "application/json")
	@ResponseBody
	public Response getAdverseEvent(@PathVariable("id") Long id) {
		logger.info("getAverseEvent");
		AdverseEvent result = adverseEventService.findById(id);
		Response response = new Response();
		response.setEntity(result);
		response.setUserRole(getUser().getUserRole().name());
		response.setEntityClass("adverseEvent");
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/adverseEvent", produces = "application/json")
	@ResponseBody
	public Response getAdverseEventNew(@RequestParam(value = "patientId") Long patientId) {
		logger.info("getAverseEventNew");
		AdverseEvent result = new AdverseEvent();
		result.setPatientId(patientId);
		Response response = new Response();
		response.setEntity(result);
		response.setUserRole(getUser().getUserRole().name());
		response.setEntityClass("adverseEvent");
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "adverseEventUpdate", produces = "application/json")
	@ResponseBody
	public Response updateAdverseEvent(@RequestBody final AdverseEvent adverseEvent) throws IOException {
		logger.info("updateAdverseEvent");
		if (adverseEvent.getId() != null) throw new AccessDeniedException("Уже отправлено");

		Response response = new Response();
		if (getUser().getUserRole().equals(UserRole.MANAGER)) {
			AdverseEvent adverseEvent1 = adverseEventService.save(adverseEvent);
			response.setEntity(adverseEvent);
			response.setEntityClass("adverseEvent");
			try {
				adverseEventService.makePdf(adverseEvent);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		response.setUserRole(getUser().getUserRole().name());
		return response;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/adverseEvents", produces = "application/json")
	@ResponseBody
	public Response getAdverseEvents(@RequestParam(value = "patientId", required = false) Long patientId, HttpServletRequest request)
			throws IOException {
		logger.info("getAdverseEvents");
		List<HashMap> adverseEvents = adverseEventService.findHashMapList(patientId);
		Response response = new Response();
		response.setData(adverseEvents);
		response.setUserRole(getUser().getUserRole().name());
		return response;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/consultationsExtramural", produces = "application/json")
	@ResponseBody
	public Response getConsultationExtramural(HttpServletRequest request)
			throws IOException {
		logger.info("getConsultationExtramural");
		ConsultationType consultationType = (ConsultationType) dictionaryService.findById("ConsultationType", 2L);
		List<PatientDTO> patientDTOList = patientService.findAll().stream().map(o -> new PatientDTO(o, getUser().getUserRole())).collect(Collectors.toList());
		if (!patientDTOList.isEmpty()) {
			patientDTOList = patientDTOList.stream().filter(patientDTO -> patientDTO.getConsultation() != null &&
					patientDTO.getConsultation().getConsultationType() != null &&
					consultationType.getId().equals(patientDTO.getConsultation().getConsultationType().getId())).collect(Collectors.toList());
		}
		Response response = new Response();
		response.setEntityClass("consultationsExtramural");
		response.setData(patientDTOList);
		response.setUserRole(getUser().getUserRole().name());
		return response;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/consultationsFullTime", produces = "application/json")
	@ResponseBody
	public Response getConsultation(HttpServletRequest request)
			throws IOException {
		logger.info("getConsultationFullTime");
		ConsultationType consultationType = (ConsultationType) dictionaryService.findById("ConsultationType", 1L);
		List<PatientDTO> patientDTOList = patientService.findAll().stream().map(o -> new PatientDTO(o, getUser().getUserRole())).collect(Collectors.toList());
		if (!patientDTOList.isEmpty()) {
			patientDTOList = patientDTOList.stream().filter(patientDTO -> patientDTO.getConsultation() != null &&
					patientDTO.getConsultation().getConsultationType() != null &&
					consultationType.getId().equals(patientDTO.getConsultation().getConsultationType().getId())).collect(Collectors.toList());
		}
		Response response = new Response();
		response.setEntityClass("consultationsFullTime");
		response.setData(patientDTOList);
		response.setUserRole(getUser().getUserRole().name());
		return response;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/legalSupports", produces = "application/json")
	@ResponseBody
	public Response getLegalSupports(HttpServletRequest request)
			throws IOException {
		logger.info("getLegalSupports");
		List<PatientDTO> patientDTOList = patientService.findAll().stream().map(o -> new PatientDTO(o, getUser().getUserRole())).collect(Collectors.toList());
		if (!patientDTOList.isEmpty()) {
			patientDTOList = patientDTOList.stream().filter(patientDTO -> patientDTO.getLegalSupport() != null).collect(Collectors.toList());
		}
		Response response = new Response();
		response.setEntityClass("legalSupports");
		response.setData(patientDTOList);
		response.setUserRole(getUser().getUserRole().name());
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/doctorExpertCenter/{id}", produces = "application/json")
	@ResponseBody
	public Response getDoctorExpertCenter(@PathVariable("id") Long id) {
		logger.info("getDoctorExpertCenter");
		Response response = new Response();
		response.setEntityClass("doctorExpertCenter");
		response.setEntity(doctorExpertCenterService.findOne(id));
		response.setUserRole(getUser().getUserRole().name());
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/doctorExpertCenter", produces = "application/json")
	@ResponseBody
	public Response getDoctorExpertCenter(@RequestParam(value = "q", required = false) String q) {
		logger.info("getDoctorExpertCenter");
		Response response = new Response();
		response.setEntityClass("doctorExpertCenter");
		response.setData(doctorExpertCenterService.findAll());
		response.setUserRole(getUser().getUserRole().name());
		return response;
	}
}
