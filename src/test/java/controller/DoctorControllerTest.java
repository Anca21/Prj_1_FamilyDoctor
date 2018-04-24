package controller;

import exceptions.PatientException;
import model.Patient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import repository.Repository;

import java.io.File;
import java.io.IOException;

public class DoctorControllerTest {

    private DoctorController ctr;

    @Rule
    public TemporaryFolder folder= new TemporaryFolder();

    @Before
    public void setUp(){
        File patient_file = null, consultation_file=null;
        try {
            patient_file = folder.newFile("patient_test.txt");
            consultation_file = folder.newFile("consultation_test.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }


        assert patient_file != null;
        assert consultation_file != null;
        Repository rep = new Repository(patient_file.getName(), consultation_file.getName());
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