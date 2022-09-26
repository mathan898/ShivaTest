package com.demo.batch;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.demo.model.Student;
import com.demo.model.Profile;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public FlatFileItemReader<Student> reader() {
		return new FlatFileItemReaderBuilder<Student>().name("studentItemReader")
				.resource(new ClassPathResource("students.csv")).delimited()
				.names(new String[] { "id", "name", "age", "email" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Student>() {
					{
						setTargetType(Student.class);
					}
				}).linesToSkip(1).build();
	}

	@Bean
	public JdbcBatchItemWriter<Profile> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Profile>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Profile>())
				.sql("INSERT INTO profile (id, name, profileName, email) VALUES (:id, :name, :profileName, :email)")
				.dataSource(dataSource).build();
	}

	@Bean
	public ItemProcessor<Student, Profile> processor() {
		return new StudentItemProcessor();
	}

	@Bean
	public Job createStudentJob(StdJobExecutionListener listener, Step step1) {
		return jobBuilderFactory.get("createStudentJob").incrementer(new RunIdIncrementer()).listener(listener)
				.flow(step1).end().build();
	}

	@Bean
	public Step step1(ItemReader<Student> reader, ItemWriter<Profile> writer,
			ItemProcessor<Student, Profile> processor) {
		return stepBuilderFactory.get("step1").<Student, Profile>chunk(5).reader(reader).processor(processor)
				.writer(writer).build();
	}

	@Bean
	public DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setJdbcUrl("jdbc:h2:tcp://localhost/~/back");
		dataSource.setUsername("sa");
		dataSource.setPassword("sa");
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
