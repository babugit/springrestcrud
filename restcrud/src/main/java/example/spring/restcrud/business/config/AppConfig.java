package example.spring.restcrud.business.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.common.base.Preconditions;

import example.spring.restcrud.business.manager.IApplicantManager;
import example.spring.restcrud.business.manager.impl.ApplicantManager;

@Configuration
@EnableTransactionManagement
@EnableWebMvc
@ComponentScan(basePackages = "example.spring.restcrud")
@PropertySource("classpath:hibernate.properties")
public class AppConfig {

	@Autowired
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		final BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("jdbc.driverClassName")));
		dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("jdbc.url")));
		dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("jdbc.userName")));
		dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("jdbc.password")));
		
		return dataSource;
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "example.spring.restcrud.database.entity" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		
		return sessionFactory;
	}
	
	@Autowired
	@Bean
	public HibernateTransactionManager transactionManager(final SessionFactory sessionFactory) {
		final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		
		return transactionManager;		
	}

	@Bean
	public IApplicantManager applicantManager() {
		return new ApplicantManager();
	}

	final Properties hibernateProperties() {
		final Properties hibernateProperties = new Properties();
		//hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		hibernateProperties.setProperty("hibernate.id.new_generator_mapping", env.getProperty("hibernate.id.new_generator_mapping"));
		hibernateProperties.setProperty("current_session_context_class", env.getProperty("current_session_context_class"));
		
		return hibernateProperties;
	}
}
