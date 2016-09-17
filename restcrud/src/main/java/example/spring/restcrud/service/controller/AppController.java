package example.spring.restcrud.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import example.spring.restcrud.business.config.ResponseStatus;
import example.spring.restcrud.business.config.Utils;
import example.spring.restcrud.business.entity.ApplicantInfo;
import example.spring.restcrud.business.manager.IApplicantManager;

@RestController
public class AppController {

	@Autowired
	IApplicantManager applicantManager;
	
	@RequestMapping(value = "/applicant/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<ApplicantInfo> getApplicant(@PathVariable("id") int id) {
		System.out.println("REQUEST LANDED AT getApplicant CONTROLLER METHOD");
		ResponseStatus responseStauts = null;
		Object object = applicantManager.getApplicantInfo(id);
		if (object instanceof ResponseStatus) {
			responseStauts = (ResponseStatus)object;
			return new ResponseEntity<ApplicantInfo>(Utils.responseToHttpStatus(responseStauts));
		}
		ApplicantInfo applicantInfo = (ApplicantInfo) object;

		return new ResponseEntity<ApplicantInfo>(applicantInfo, Utils.responseToHttpStatus(responseStauts));
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/applicants/", params = { "id", "name", "gender", "position", "email" }, 
					method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ApplicantInfo>> getApplicants(@RequestParam("id") int id, @RequestParam("name") String name, 
															@RequestParam(value = "gender") String gender, 
															@RequestParam("position") String position, @RequestParam("email") String email) {
		
		System.out.println("REQUEST LANDED AT getApplicants CONTROLLER METHOD");
		ResponseStatus responseStatus = null;
		Object object = applicantManager.getApplicantsInfo(id, name, gender, position, email);
		if (object instanceof ResponseStatus) {
			responseStatus = (ResponseStatus) object;
			return new ResponseEntity<List<ApplicantInfo>>(Utils.responseToHttpStatus(responseStatus));
		}
		List<ApplicantInfo> applicants = (List<ApplicantInfo>) object;
		
		return new ResponseEntity<List<ApplicantInfo>>(applicants, Utils.responseToHttpStatus(responseStatus));
	}
	
	@RequestMapping(value = "/applicant/", method = RequestMethod.POST) 
	public ResponseEntity<Void> createApplicant(@RequestBody ApplicantInfo applicantInfo, UriComponentsBuilder ucBuilder) {		
		System.out.println("REQUEST LANDED AT createApplicant CONTROLLER METHOD");
		ResponseStatus responseStauts = null;
		Object object = applicantManager.createApplicant(applicantInfo);
		if (object instanceof ResponseStatus) {
			responseStauts = (ResponseStatus)object;
			return new ResponseEntity<Void>(Utils.responseToHttpStatus(responseStauts));
		}
		int id = ((Integer) object).intValue();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/applicant/{id}").buildAndExpand(id).toUri());
		
		return new ResponseEntity<Void>(headers, Utils.responseToHttpStatus(responseStauts));
	}
	
	@RequestMapping(value = "/applicant/{id}/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<ApplicantInfo> editApplicant(@PathVariable int id, @RequestBody ApplicantInfo applicantInfo) {
		System.out.println("REQUEST LANDED AT editApplicant CONTROLLER METHOD");
		ResponseStatus responseStauts = null;
		Object object = applicantManager.editApplicant(applicantInfo);
		if (object instanceof ResponseStatus) {
			responseStauts = (ResponseStatus)object;
			return new ResponseEntity<ApplicantInfo>(applicantInfo, Utils.responseToHttpStatus(responseStauts));
		}
		applicantInfo = (ApplicantInfo) object;

		return new ResponseEntity<ApplicantInfo>(applicantInfo, Utils.responseToHttpStatus(responseStauts));
	}
	
	@RequestMapping(value = "/applicant/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseStatus> deleteApplicant(@PathVariable int id) {
		System.out.println("REQUEST LANDED AT deleteApplicant CONTROLLER METHOD");
		Object object = applicantManager.deleteApplicantsById(id);
		ResponseStatus responseStauts = (ResponseStatus) object;
		
		return new ResponseEntity<ResponseStatus>(responseStauts, Utils.responseToHttpStatus(responseStauts));
	}
	
}
