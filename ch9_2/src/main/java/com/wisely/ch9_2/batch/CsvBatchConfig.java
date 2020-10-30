package com.wisely.ch9_2.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.wisely.ch9_2.domain.Person;

/** 9.2：使用@EnableBatchProcessing开启批处理的支持。 */
@Configuration
@EnableBatchProcessing
public class CsvBatchConfig {
	@Bean
	public ItemReader<Person> reader() throws Exception {
		/** 9.2:1-1.使用FlatFileItemReader读取文件。 */
		FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
		/** 9.2:1-2.使用FlatFileItemReader的setResource方法设置csv文件的路径。 */
		reader.setResource(new ClassPathResource("people.csv"));
		/** 9.2:1-3.在此处对cvs文件的数据和领域模型类做对应映射。 */
		reader.setLineMapper(new DefaultLineMapper<Person>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setNames(new String[] {"name","age","nation","address"});
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
				setTargetType(Person.class);
			}});
		}});
		return reader;
	}
	
	@Bean
	public ItemProcessor<Person, Person>processor() {
		/** 9.2:2-1.使用我们自己定义的ItemProcessor的实现CsvItemProcessor。 */
		CsvItemProcessor processor = new CsvItemProcessor();
		/** 9.2:2-2.为processor指定校验器为CsvBeanValidator。 */
		processor.setValidator(csvBeanValidator());
		return processor;
	}
	
	/** 9.2:3-1.Spring能让容器中已有的Bean以参数的形式注入，Spring Boot已为我们定义了dataSource。 */
	@Bean
	public ItemWriter<Person> writer(DataSource dataSource) {
		/** 9.2:3-2.我们使用JDBC批处理的JdbcBatchItemWriter来写数据到数据库。 */
		JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
		String sql = "insert into person " +"(id,name,age,nation,address) "
				+"values(hibernate_sequence.nextval, :name, :age, :nation, :address)";
		/** 9.2:3-3.在此设置要执行批处理的SQL语句。 */
		writer.setSql(sql);
		writer.setDataSource(dataSource);
		return writer;
	}
	
	/** 9.2:4.jobRepository的定义需要dataSource和transactionManager，Spring Boot已为我们自动配置了这两个类，Spring可通过方法注入已有的Bean。 */
	@Bean
	public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setDataSource(dataSource);
		jobRepositoryFactoryBean.setTransactionManager(transactionManager);
		jobRepositoryFactoryBean.setDatabaseType("oracle");
		
		return jobRepositoryFactoryBean.getObject();
	}
	
	/** 9.2：5.JobLauncher定义。 */
	@Bean
	public SimpleJobLauncher jobLauncher(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository(dataSource, transactionManager));
		
		return jobLauncher;
	}
	
	/** 9.2：6.Job定义。 */
	@Bean
	public Job importJob(JobBuilderFactory jobs, Step s1) {
		return jobs.get("importJob")
					.incrementer(new RunIdIncrementer())
					/** 9.2：6-1，为Job指定Step。 */
					.flow(s1)
					.end()
					/** 9.2：6-2，绑定监听器csvJobListener。 */
					.listener(CsvJobListener())
					.build();
	}
	
	/** 9.2：7.Step定义。 */
	@Bean
	public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Person> reader,
						ItemWriter<Person> writer, ItemProcessor<Person,Person> processor) {
		return stepBuilderFactory.get("step1")
									/** 9.2：7-1，批处理每次提交65000条数据。 */
									.<Person, Person>chunk(65000)
									/** 9.2：7-2，给step绑定reader。 */
									.reader(reader)
									/** 9.2：7-3，给step绑定processor。 */
									.processor(processor)
									/** 9.2：7-4，给step绑定writer。 */
									.writer(writer)
									.build();
	}

	@Bean
	public CsvJobListener CsvJobListener() {
		return new CsvJobListener();
	}
	
	@Bean
	public Validator<Person> csvBeanValidator() {
		return new CsvBeanValidator<Person>();
	}
	
	
}






