package controller;

import exceptions.ConsultationException;
import exceptions.PatientException;
import model.Consultation;
import model.Patient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import repository.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DoctorControllerTest {

    private DoctorController ctr;
    private Repository rep;

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
        rep = new Repository(patient_file.getName(), consultation_file.getName());
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

        assertNotEquals(-1, ctr.getPatientBySSN("1212121212121"));
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

    @Test
    public void addConsultationWithSuccess() throws PatientException, ConsultationException {
        Patient p = new Patient("1212121212121", "Valid Name", "valid address 123");
        ctr.addPatient(p);

        List<String> meds = new ArrayList<>();
        meds.add("med1");
        meds.add("med2");
        Consultation c = new Consultation();
        c.setConsID("55");
        c.setConsultation_date("10-10-2010");
        c.setDiag("diag");
        c.setMeds(meds);

        c.setPatientSSN("1212121212121");
        ctr.addConsultation(c);
        assertNotEquals(-1, ctr.getConsByID("55"));
    }

    @Test(expected = ConsultationException.class)
    public void addConsultationWithNonExistingPatientSSN() throws ConsultationException {
        List<String> meds = new ArrayList<>();
        meds.add("med1");
        meds.add("med2");
        Consultation c = new Consultation();
        c.setConsID("55");
        c.setConsultation_date("10-10-2010");
        c.setDiag("diag");
        c.setMeds(meds);
        c.setPatientSSN("0000000000000");

        ctr.addConsultation(c);
    }

    @Test(expected = ConsultationException.class)
    public void addConsultationWithInvalidDate() throws ConsultationException, PatientException {
        Patient p = new Patient("1212121212121", "Valid Name", "valid address 123");
        ctr.addPatient(p);

        List<String> meds = new ArrayList<>();
        meds.add("med1");
        meds.add("med2");
        Consultation c = new Consultation();
        c.setConsID("55");
        c.setConsultation_date("10-100-2010");
        c.setDiag("diag");
        c.setMeds(meds);
        c.setPatientSSN("1212121212121");

        ctr.addConsultation(c);
    }

    @Test(expected = ConsultationException.class)
    public void addConsultationWithInvalidDate2() throws ConsultationException, PatientException {
        Patient p = new Patient("1212121212121", "Valid Name", "valid address 123");
        ctr.addPatient(p);

        List<String> meds = new ArrayList<>();
        meds.add("med1");
        meds.add("med2");
        Consultation c = new Consultation();
        c.setConsID("55");
        c.setConsultation_date("10-100");
        c.setDiag("diag");
        c.setMeds(meds);
        c.setPatientSSN("1212121212121");

        ctr.addConsultation(c);
    }

    @Test(expected = ConsultationException.class)
    public void addConsultationWithInvalidDate3() throws ConsultationException, PatientException {
        Patient p = new Patient("1212121212121", "Valid Name", "valid address 123");
        ctr.addPatient(p);

        List<String> meds = new ArrayList<>();
        meds.add("med1");
        meds.add("med2");
        Consultation c = new Consultation();
        c.setConsID("55");
        c.setConsultation_date("ab-ab-abcd");
        c.setDiag("diag");
        c.setMeds(meds);
        c.setPatientSSN("1212121212121");

        ctr.addConsultation(c);
    }

    @Test(expected = ConsultationException.class)
    public void addConsultationWithNullMeds() throws ConsultationException, PatientException {
        Patient p = new Patient("1212121212122", "Valid Name", "valid address 123");
        ctr.addPatient(p);

        List<String> meds = null;

        Consultation c = new Consultation();
        c.setConsID("55");
        c.setConsultation_date("10-10-2010");
        c.setDiag("diag");
        c.setMeds(meds);
        c.setPatientSSN("1212121212122");

        ctr.addConsultation(c);
    }
    @Test(expected = ConsultationException.class)
    public void addConsultationWithEmptyMeds() throws ConsultationException, PatientException {
        Patient p = new Patient("1212121212124", "Valid Name", "valid address 123");
        ctr.addPatient(p);

        List<String> meds = new ArrayList<>();

        Consultation c = new Consultation();
        c.setConsID("55");
        c.setConsultation_date("10-10-2010");
        c.setDiag("diag");
        c.setMeds(meds);
        c.setPatientSSN("1212121212124");

        ctr.addConsultation(c);
    }

    @Test
    public void getExistingConsultationById() throws PatientException, ConsultationException {

        Patient p = new Patient("1212121212125", "Valid Name", "valid address 123");
        ctr.addPatient(p);

        List<String> meds = new ArrayList<>();
        meds.add("med1");
        meds.add("med2");
        Consultation c = new Consultation();
        c.setConsID("55");
        c.setConsultation_date("10-10-2010");
        c.setDiag("diag");
        c.setMeds(meds);
        c.setPatientSSN("1212121212125");

        ctr.addConsultation(c);

        assertNotEquals(-1, ctr.getConsByID("55"));
    }

    @Test
    public void getNonExistingConsultationById() throws PatientException, ConsultationException {

        Patient p = new Patient("1212121212127", "Valid Name", "valid address 123");
        ctr.addPatient(p);

        List<String> meds = new ArrayList<>();
        meds.add("med1");
        meds.add("med2");
        Consultation c = new Consultation();
        c.setConsID("55");
        c.setConsultation_date("10-10-2010");
        c.setDiag("diag");
        c.setMeds(meds);
        c.setPatientSSN("1212121212127");

        ctr.addConsultation(c);

        assertEquals(-1, ctr.getConsByID("58"));
    }

    @Test
    public void getNonExistingPatientBySSN() throws PatientException, ConsultationException {

        Patient p = new Patient("1212121212127", "Valid Name", "valid address 123");
        ctr.addPatient(p);

        assertEquals(-1, ctr.getPatientBySSN("1212121212124"));
    }

    @Test
    public void getExistingPatientBySSN() throws PatientException, ConsultationException {

        Patient p = new Patient("1212121212127", "Valid Name", "valid address 123");
        ctr.addPatient(p);

        assertNotEquals(-1, ctr.getPatientBySSN("1212121212127"));
    }

}