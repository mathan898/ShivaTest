package com.demo.batch;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.demo.model.Profile;

@Component
public class StdJobExecutionListener implements JobExecutionListener {
	
	@Autowired
	public JdbcTemplate jdbcTemplate;

	@Override
	public void beforeJob(JobExecution jobExecution) {
	    System.out.println("Executing job id " + jobExecution.getId());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
	    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
	        List<Profile> result = jdbcTemplate.query("SELECT id, name, profileName, email FROM profile", 
	        		new RowMapper<Profile>() {
	            @Override
	            public Profile mapRow(ResultSet rs, int row) throws SQLException {
	                return new Profile(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4));
	            }
	        });
	        System.out.println("Number of Records:"+result.size());
	    }
	}

}
