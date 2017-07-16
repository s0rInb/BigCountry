package com.gmail.s0rInb.controller;

import com.gmail.s0rInb.entities.Files.Files;
import com.gmail.s0rInb.entities.Files.InfoConsentFile;
import com.gmail.s0rInb.entities.Patient;
import com.gmail.s0rInb.repository.FileRepository;
import com.gmail.s0rInb.service.PatientService;
import com.gmail.s0rInb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
public class AppController {
    final Logger logger = LoggerFactory.getLogger(AppController.class);

	@Autowired
    UserService userService;
	@Autowired
    PatientService patientService;
    @Autowired
    FileRepository fileRepository;
//    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}", produces = "application/json")
//    @ResponseBody
//    public Response getStaff(@PathVariable("id") Long id) {
//        User result = userService.findById(id);
//        Response response = new Response();
//        response.setEntity(result);
//        response.setEntityClass("user");
//        return response;
//    }
//
//    @RequestMapping(method = RequestMethod.GET, value = "/usersAll", produces = "application/json")
//    @ResponseBody
//    public Response getUsers(HttpServletRequest request,
//                             @RequestParam(value = "start", required = false) int start,
//                             @RequestParam(value = "length", required = false) int rows,
//                             @RequestParam(value = "order[0][column]", required = false) int orderColNum,
//                             @RequestParam(value = "order[0][dir]", required = false) String order)
//            throws IOException{
//        int page = start / rows;
//
//        String sortBy = request.getParameter("columns[" + orderColNum + "][data]");
//        logger.info("Listing users for grid with page: {}, rows: {}", page + 1, rows);
//        logger.info("Listing users for grid with sort: {}, order: {}", sortBy, order);
//        Sort sort = null;
//        if (order != null) {
//            if (order.equals("desc")) {
//                sort = new Sort(Sort.Direction.DESC, sortBy);
//            } else
//                sort = new Sort(Sort.Direction.ASC, sortBy);
//        }
//        PageRequest pageRequest;
//        if (sort != null) {
//            pageRequest = new PageRequest(page, rows, sort);
//        } else {
//            pageRequest = new PageRequest(page, rows);
//        }
//        Page<User> result = userService.findAllByPage(pageRequest);
//        Response response = new Response();
//        response.setDraw(Integer.parseInt(request.getParameter("draw")));
//        response.setRecordsFiltered((int) result.getTotalElements());
//        response.setRecordsTotal(result.getTotalElements());
//        response.setData(result.getContent());
//        return response;
//    }
//    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}", produces = "application/json")
//    @ResponseBody
//    public Response getStaff(@PathVariable("id") Long id) {
//        User result = userService.findById(id);
//        Response response = new Response();
//        response.setEntity(result);
//        response.setEntityClass("user");
//        return response;
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/patients", produces = "application/json")
    @ResponseBody
    public Response getUsers(HttpServletRequest request)
            throws IOException{
        List<Patient> result = patientService.findAll();
        Response response = new Response();
        response.setData(result);
        return response;
    }
    @RequestMapping(method = RequestMethod.GET, value = "/patient/{id}", produces = "application/json")
    @ResponseBody
    public Response getPatient(@PathVariable("id") Long id) {
        Patient result = patientService.findById(id);
        Response response = new Response();
        response.setEntity(result);
        response.setEntityClass("patient");
        return response;
    }
    @RequestMapping(method = RequestMethod.POST, value = "patientUpdate", produces = "application/json")
    @ResponseBody
    public Response updatePatient(@Valid @RequestBody final Patient patient) {
        Response response = new Response();
        Patient patient1 = patientService.save(patient);
        response.setEntity(patient1);
        response.setEntityClass("product");
        return response;
    }
    @RequestMapping(method = RequestMethod.POST, value = "uploadFile", consumes = {"multipart/form-data"}, produces = "application/json")
    @ResponseBody
    public Response uploadFile(@RequestPart("file") MultipartFile file,
                               @RequestParam(value = "catalog", required = false) String catalog,
                               @RequestParam(value = "fileCount", required = false) String fileCount,
                               @RequestParam(value = "id", required = false) Long entityId){
        InfoConsentFile infoConsentFile = new InfoConsentFile();
        try {
            infoConsentFile.setFile(file.getBytes());
        } catch (java.io.IOException e){

        }
        infoConsentFile.setPatient(patientService.findById(entityId));
        fileRepository.save(infoConsentFile);
        return uploadAnyFile(file,catalog,fileCount,entityId,false);
    }
    @RequestMapping(method = RequestMethod.POST, value = "uploadInfoConsentFile", consumes = {"multipart/form-data"}, produces = "application/json")
    @ResponseBody
    public Response uploadInfoConsentFile(@RequestPart("file") MultipartFile file,
                               @RequestParam(value = "patient_id", required = false) Long id,
                               @RequestParam(value = "fileCount", required = false) String fileCount,
                               @RequestParam(value = "id", required = false) Long entityId){
        return uploadAnyFile(file,null,null,entityId,false);
    }
    private Response uploadAnyFile(MultipartFile file, String catalog, String fileCount, Long entityId, boolean isImage){
        Response response = new Response();
//        String path;
//        try {
//            path = fileStorageService.upload(file, catalog, "files", entityId);
//        } catch (FileStorageException e) {
//            throw new FileStorageException(e);
//        }
        HashMap<String, Object> fileParameters = new HashMap();
//        fileParameters.put("path",path);
        fileParameters.put("fileName",file.getOriginalFilename());
        fileParameters.put("fileCount",fileCount);
        if (isImage){
            StringBuilder sb = new StringBuilder();
            sb.append("data:image/jpeg;base64,");
//            try {
//                sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(file.getBytes(), false)));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            fileParameters.put("preview", sb.toString());
        }
        response.setEntity(fileParameters);
        response.setEntityClass("filePath");
        return response;
    }

//    private MultipartFile scale(MultipartFile fileData) {
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//        try {
//            ByteArrayInputStream in = new ByteArrayInputStream(fileData.getBytes());
//            BufferedImage img = ImageIO.read(in);
//            double width;
//            double height;
//            if (img.getWidth()>img.getHeight()) {
//                width = 240;
//                height = ((double) img.getHeight()) / ((double) img.getWidth() / width);
//            } else {
//                height = 320;
//                width = ((double) img.getWidth()) / ((double) img.getHeight() / height);
//            }
//            Image scaledImage = img.getScaledInstance((int)width, (int)height, Image.SCALE_DEFAULT);
//            BufferedImage imageBuff = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_RGB);
//            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0,0,0), null);
//            ImageIO.write(imageBuff, "jpg", buffer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new MultipartFileImpl(buffer.toByteArray(),"photo");
//    }
}
