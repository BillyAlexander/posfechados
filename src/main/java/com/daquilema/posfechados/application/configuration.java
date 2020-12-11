package com.daquilema.posfechados.application;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.daquilema.posfechados.utils.Const;

@Configuration
public class configuration {
	
	@Value("${spring.jpa.generate-ddl}")
	private Boolean generateDll;

	@Value("${spring.jpa.show-sql}")
	private Boolean showSql;
	
	@Bean
	public DataSource dataBaseDataSource() throws NamingException {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("org.postgresql.Driver");
		dataSourceBuilder.url("jdbc:postgresql://ec2-54-208-233-243.compute-1.amazonaws.com:5432/d7t2uvjj3nvagd");
		dataSourceBuilder.username("lxzehfswwiupap");
		dataSourceBuilder.password("a3acc89c74d33938d32f8d00842c9c7f4949be1d999ec1927b365ba5dc22cff5");
		return dataSourceBuilder.build();
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() throws NamingException {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(this.generateDll);
		vendorAdapter.setShowSql(this.showSql);
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(Const.PACKAGE_NAMING);
		factory.setDataSource(this.dataBaseDataSource());
		Properties properties = new Properties();
		properties.setProperty("hibernate.jdbc.batch_size", "100");
		properties.setProperty("hibernate.order_inserts", "true");
		properties.setProperty("hibernate.order_updates", "true");
		properties.setProperty("hibernate.event.merge.entity_copy_observer", "allow");
		factory.setJpaProperties(properties);
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
