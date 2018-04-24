package controller;

import exceptions.PatientException;
import model.Patient;
import org.junit.Before;
import org.junit.Test;
import repository.Repository;

public class DoctorControllerTest {

    private DoctorController ctr;

    @Before
    public void setUp(){
        Repository rep = new Repository("patient_test.txt", "consultation_test.txt");
        ctr = new DoctorController(rep);
    }

    @Test()
    public void addPatientWithSuccess() throws PatientException {
        Patient p = new Patient();
        p.setPatient_ID("1");
        p.setSSN("1212121212121");
        p.setAddress("add");
        p.setName("name");

        ctr.addPatient(p);
    }

    @Test(expected = PatientException.class)
    public void addPatientWith12CharactersSSN() throws PatientException {
        Patient p = new Patient();
        p.setPatient_ID("1");
        p.setSSN("121212121212");
        p.setAddress("add");
        p.setName("name");

        ctr.addPatient(p);
    }

    @Test(expected = PatientException.class)
    public void addPatientWith14CharactersSSN() throws PatientException {
        Patient p = new Patient();
        p.setPatient_ID("1");
        p.setSSN("12121212121212");
        p.setAddress("add");
        p.setName("name");

        ctr.addPatient(p);
    }

    @Test(expected = PatientException.class)
    public void addPatientWithInvalidName() throws PatientException {
        Patient p = new Patient();
        p.setPatient_ID("1");
        p.setSSN("1212121212121");
        p.setAddress("add");
        p.setName("Name123");

        ctr.addPatient(p);
    }

    @Test(expected = PatientException.class)
    public void addPatientWithEmptyName() throws PatientException {
        Patient p = new Patient();
        p.setPatient_ID("1");
        p.setSSN("1212121212121");
        p.setAddress("add");
        p.setName("");

        ctr.addPatient(p);
    }

    @Test(expected = PatientException.class)
    public void addPatientWithEmptyAddress() throws PatientException {
        Patient p = new Patient();
        p.setPatient_ID("1");
        p.setSSN("1212121212121");
        p.setAddress("");
        p.setName("Valid Name");

        ctr.addPatient(p);
    }

}