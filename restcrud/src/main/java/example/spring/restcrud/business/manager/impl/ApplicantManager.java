package example.spring.restcrud.business.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.spring.restcrud.business.config.ResponseStatus;
import example.spring.restcrud.business.config.Utils;
import example.spring.restcrud.business.entity.ApplicantInfo;
import example.spring.restcrud.business.manager.AbstractManager;
import example.spring.restcrud.business.manager.IApplicantManager;
import example.spring.restcrud.database.entity.Applicant;
import example.spring.restcrud.database.manager.IApplicantDbManager;
import example.spring.restcrud.database.manager.IOperations;

@Service
public class ApplicantManager extends AbstractManager<Applicant> implements IApplicantManager {

	@Autowired
	private IApplicantDbManager dbManager;

	public ApplicantManager() {
		super();
	}

	@Override
	protected IOperations<Applicant> getDbManager() {
		return dbManager;
	}

	@Override
	public Object createApplicant(ApplicantInfo applicantInfo) {
		System.out.println("REQUEST LANDED AT createApplicant BUSINESSMANAGER METHOD");
		if (applicantInfo == null) {
			return ResponseStatus.INVALID;
		}
		try {
			Object object = create(prepareApplicantsEO(applicantInfo, null));
			return ((Integer) object).intValue();
		} catch (Throwable e) {
			e.printStackTrace();
			return ResponseStatus.UNDETERMINED;
		}
	}

	@Override
	public Object deleteApplicantsById(int id) {
		System.out.println("REQUEST LANDED AT deleteApplicantsById BUSINESSMANAGER METHOD");
		if (Utils.isEmpty(id)) {
			return ResponseStatus.INVALID;
		}
		try {
			deleteById(id);			
			return ResponseStatus.SUCCESS;			
		} catch (Throwable e) {
			e.printStackTrace();
			return ResponseStatus.UNDETERMINED;
		}
	}

	@Override
	public Object deleteApplicant(ApplicantInfo applicantInfo) {
		System.out.println("REQUEST LANDED AT deleteApplicant BUSINESSMANAGER METHOD");
		if (applicantInfo == null) {
			return ResponseStatus.INVALID;
		}
		try {
			delete(prepareApplicantsEO(applicantInfo, null));
			return ResponseStatus.SUCCESS;
		} catch (Throwable e) {
			e.printStackTrace();
			return ResponseStatus.UNDETERMINED;
		}
	}

	@Override
	public Object editApplicant(ApplicantInfo applicantInfo) {
		System.out.println("REQUEST LANDED AT editApplicant BUSINESSMANAGER METHOD");
		if (applicantInfo == null || Utils.isEmpty(applicantInfo.getId())) {
			return ResponseStatus.INVALID;
		}
		try {
			Object object = findOneById(applicantInfo.getId());
			if (object == null) {
				return ResponseStatus.FAILED;
			}
			example.spring.restcrud.database.entity.Applicant applicant = (example.spring.restcrud.database.entity.Applicant) object;
			Object objectDb = update(prepareApplicantsEO(applicantInfo, applicant));
			if (objectDb == null) {
				return ResponseStatus.FAILED;
			}
			applicant = (example.spring.restcrud.database.entity.Applicant) objectDb;
			
			return new ApplicantInfo(applicant.getId(), applicant.getName(), applicant.getEmail(),
										applicant.getGender(), applicant.getPosition(), applicant.getSkills());
		} catch (Throwable e) {
			e.printStackTrace();
			return ResponseStatus.UNDETERMINED;
		}
	}

	@Override
	public Object getAllApplicants() {
		System.out.println("REQUEST LANDED AT getAllApplicants BUSINESSMANAGER METHOD");
		List<ApplicantInfo> applicantInfos = new ArrayList<ApplicantInfo>();
		try {
			List<example.spring.restcrud.database.entity.Applicant> applicants = findAll();
			if (applicants == null) {
				return ResponseStatus.FAILED;
			}
			applicants.stream().forEach((applicant) -> {
				if (applicant != null) {
					applicantInfos.add(new ApplicantInfo(applicant.getId(), applicant.getName(), applicant.getEmail(),
							applicant.getGender(), applicant.getPosition(), applicant.getSkills()));
				}
			});		
		} catch (Throwable e) {
			e.printStackTrace();
			return ResponseStatus.UNDETERMINED;
		}

		return applicantInfos;
	}

	@Override
	public Object getApplicantsInfo(Integer id, String name, String gender, String position, String email) {
		System.out.println("REQUEST LANDED AT getApplicantsInfo BUSINESSMANAGER METHOD");
		if (Utils.isEmpty(id) && Utils.isEmpty(name) && Utils.isEmpty(gender) 
				&& Utils.isEmpty(position) && Utils.isEmpty(email)) {
			return getAllApplicants();
		}
		
		List<ApplicantInfo> applicantInfos = new ArrayList<ApplicantInfo>();
		Map<String, Object> eqMap = new HashMap<String, Object>();
		eqMap.put("id", id);
		eqMap.put("gender", gender);
		eqMap.put("position", position);
		eqMap.put("email", email);

		Map<String, Object> likMap = new HashMap<String, Object>();
		likMap.put("name", name);

		try {
			List<example.spring.restcrud.database.entity.Applicant> applicants = findByCriteria(eqMap, null, likMap, MatchMode.ANYWHERE);
			if (applicants == null) {
				return ResponseStatus.FAILED;
			}
			applicants.stream().forEach((applicant) -> {
				if (applicant != null) {
					applicantInfos.add(new ApplicantInfo(applicant.getId(), applicant.getName(), applicant.getEmail(),
							applicant.getGender(), applicant.getPosition(), applicant.getSkills()));
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
			return ResponseStatus.UNDETERMINED;
		}
		
		return applicantInfos;
	}

	@Override
	public Object getApplicantInfo(Integer id) {
		System.out.println("REQUEST LANDED AT getApplicantInfo BUSINESSMANAGER METHOD");
		if (Utils.isEmpty(id)) {
			return ResponseStatus.INVALID;
		}
		
		ApplicantInfo applicantInfo = null;
		try {
			Object object = findOneById(id);
			if (object == null) {
				return ResponseStatus.FAILED;
			}
			example.spring.restcrud.database.entity.Applicant applicant = (example.spring.restcrud.database.entity.Applicant) object;
			applicantInfo = new ApplicantInfo(applicant.getId(), applicant.getName(), applicant.getEmail(), 
												applicant.getGender(), applicant.getPosition(), applicant.getSkills());		
		} catch (Throwable e) {
			e.printStackTrace();
			return ResponseStatus.UNDETERMINED;
		}

		return applicantInfo;
	}

	private example.spring.restcrud.database.entity.Applicant prepareApplicantsEO(ApplicantInfo applicantInfo,
			example.spring.restcrud.database.entity.Applicant applicantEO) {
		if (applicantEO == null) {
			applicantEO = new example.spring.restcrud.database.entity.Applicant();
		}
		
		applicantEO.setName(applicantInfo.getName());
		applicantEO.setGender(applicantInfo.getGender());
		applicantEO.setPosition(applicantInfo.getPosition());
		applicantEO.setSkills(Utils.arrayToString(applicantInfo.getSkills()));
		applicantEO.setEmail(applicantInfo.getEmail());

		return applicantEO;
	}

}