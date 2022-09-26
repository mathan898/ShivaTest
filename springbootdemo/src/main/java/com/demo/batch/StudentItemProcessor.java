package com.demo.batch;

import org.springframework.batch.item.ItemProcessor;
import com.demo.model.Student;
import com.demo.model.Profile;

public class StudentItemProcessor implements ItemProcessor<Student, Profile> {
	
	@Override
    public Profile process(final Student emp) throws Exception {
	String profileName = "";
        if (emp.getAge() < 15) {
        	profileName = "Primary";
        } else if (emp.getAge() >= 15 && emp.getAge() <= 16) {
        	profileName = "SSLC";
        } else if (emp.getAge() >=17 && emp.getAge() <= 18) {
        	profileName = "HSC";
        } else if (emp.getAge() > 18) {
        	profileName = "Under Graduate";
        }
        System.out.println("Std Code: " + emp.getId() + 
        		", Std Name: " + emp.getName() + ", Profile Name:" + profileName + ", email:" + emp.getEmail());
	return new Profile(emp.getId(), emp.getName(), profileName, emp.getEmail());
    }

}
