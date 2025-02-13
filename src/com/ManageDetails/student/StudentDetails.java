package com.ManageDetails.student;

import java.util.Date;

public interface StudentDetails {

    abstract String getAdmissionNo();

    abstract String getName();

    abstract int getAge();

    abstract int getGrade();

    abstract String getSubgrade();

    abstract String getMedium();

    abstract Date getDateOfBirth();

    abstract String getTelephone();

    abstract String getAddress();

    abstract String getGender();

    abstract String getGuardian();

    abstract String getEmail();
}
