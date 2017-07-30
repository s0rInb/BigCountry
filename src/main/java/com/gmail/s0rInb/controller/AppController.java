package com.gmail.s0rInb.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gmail.s0rInb.Utils.FileStorageExceptions;
import com.gmail.s0rInb.entities.FileLink;
import com.gmail.s0rInb.entities.Patient;
import com.gmail.s0rInb.entities.nis.AdverseEvent;
import com.gmail.s0rInb.repository.FileRepository;
import com.gmail.s0rInb.service.AdverseEventService;
import com.gmail.s0rInb.service.PatientService;
import com.gmail.s0rInb.service.UserService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import static org.hibernate.internal.util.io.StreamCopier.BUFFER_SIZE;

@Controller
public class AppController extends BaseController{
    final Logger logger = LoggerFactory.getLogger(AppController.class);
	private final static String VIDEO_UPLOAD = System.getProperty("videoUploadDir");
	@Override
	protected void init() {

	}
	@Autowired
    UserService userService;
	@Autowired
    PatientService patientService;
    @Autowired
    FileRepository fileRepository;

    @Autowired
	AdverseEventService adverseEventService;

    @RequestMapping(method = RequestMethod.GET, value = "/patients", produces = "application/json")
    @ResponseBody
    public Response getPatients(HttpServletRequest request)
            throws IOException{
    	logger.info("getPatients");
        List<Patient> result = patientService.findAll();
        Response response = new Response();
        response.setData(result);
        return response;
    }
    @RequestMapping(method = RequestMethod.GET, value = "/patient/{id}", produces = "application/json")
    @ResponseBody
    public Response getPatient(@PathVariable("id") Long id) {
		logger.info("getPatient");
        Patient result = patientService.findById(id);
        Response response = new Response();
        response.setEntity(result);
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
        response.setEntityClass("patient");
        return response;
    }
    @RequestMapping(method = RequestMethod.POST, value = "patientUpdate", produces = "application/json")
    @ResponseBody
    public Response updatePatient(@RequestBody final Patient patient){
		logger.info("updatePatient");
        Response response = new Response();
        Patient patient1 = patientService.save(patient);
        response.setEntity(patient1);
        response.setEntityClass("patient");
        return response;
    }
    @RequestMapping(method = RequestMethod.GET, value = "patientDelete/{id}", produces = "application/json")
    @ResponseBody
    public HttpStatus deletePatient(@PathVariable("id") Long id) {
		logger.info("deletePatient");
        patientService.delete(patientService.findById(id));
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
		return response;
	}
	@RequestMapping(method = RequestMethod.POST, value = "/deleteFileLinks/{id}", produces = "application/json")
	@ResponseBody
	public Response deleteFileLinks(@PathVariable("id") Long id) throws IOException {
		logger.info("deleteFileLinks");
		FileLink one = fileRepository.findOne(id);
		fileRepository.delete(id);
		Files.delete(Paths.get(one.getPath()));
		Response response = new Response();
		response.setEntityClass("fileLinks");
		return response;
	}
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, consumes = {"multipart/form-data"},produces = "application/json")
	@ResponseBody
	public HttpStatus uploadFile(@RequestParam("patientId") Long patientId,
											@RequestParam("fileType") String fileType,
											@RequestParam("name") String name,
											@RequestPart("file") MultipartFile file) {
		logger.info("uploadFile");
		if (file.isEmpty()) {
			logger.info("file is empty: HttpStatus.NO_CONTENT");
			return HttpStatus.NO_CONTENT;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
		String fileName = patientId+fileType+LocalDateTime.now().format(formatter) + "-" + name;
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

		return HttpStatus.OK;
	}
	@RequestMapping(value = "getFile",method = RequestMethod.GET)
	public void getFile(@RequestParam("id") Long id,HttpServletResponse response, HttpServletRequest request) throws IOException {
		logger.info("getFile");
		FileLink fileLink = fileRepository.findOne(id);
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
		String headerValue = String.format("attachment; filename=\"%s\"",
				downloadFile.getName());
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
	@RequestMapping(method = RequestMethod.GET, value = "/adverseEvent/{id}", produces = "application/json")
	@ResponseBody
	public Response getAdverseEvent(@PathVariable("id") Long id) {
		logger.info("getAverseEvent");
		AdverseEvent result = adverseEventService.findById(id);
		Response response = new Response();
		response.setEntity(result);
		response.setEntityClass("adverseEvent");
		return response;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/adverseEvent", produces = "application/json")
	@ResponseBody
	public Response getAdverseEvent() {
		logger.info("getAverseEvent");
		AdverseEvent result = adverseEventService.findById(0L);
		Response response = new Response();
		response.setEntity(result);
		response.setEntityClass("adverseEvent");
		return response;
	}
	@RequestMapping(method = RequestMethod.POST, value = "adverseEventUpdate", produces = "application/json")
	@ResponseBody
	public Response updateAdverseEvent(@RequestBody final AdverseEvent adverseEvent){
		logger.info("updateAdverseEvent");
		Response response = new Response();
		AdverseEvent adverseEvent1 = adverseEventService.save(adverseEvent);
		response.setEntity(adverseEvent);
		response.setEntityClass("adverseEvent");
		return response;
	}
}
