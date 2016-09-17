package example.spring.restcrud.database.manager.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import example.spring.restcrud.database.entity.Applicant;
import example.spring.restcrud.database.manager.AbstractHibernateManager;
import example.spring.restcrud.database.manager.IApplicantDbManager;

@Repository
@Transactional
public class ApplicantDbManager extends AbstractHibernateManager<Applicant> implements IApplicantDbManager {

	public ApplicantDbManager() {
		super();
		
		setClazz(Applicant.class);
	}

}
