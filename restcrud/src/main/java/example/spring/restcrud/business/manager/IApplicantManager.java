package example.spring.restcrud.business.manager;

import example.spring.restcrud.business.entity.ApplicantInfo;

public interface IApplicantManager {
	
	Object getApplicantInfo(Integer id);
	
	Object getApplicantsInfo(Integer id, String name, String gender, String position, String email);
	
	Object createApplicant(ApplicantInfo applicantInfo);
	
	Object deleteApplicantsById(final int id);
	
	Object deleteApplicant(ApplicantInfo applicantInfo);
	
	Object editApplicant(ApplicantInfo applicantInfo);
	
	Object getAllApplicants();
}