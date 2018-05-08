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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class DoctorControllerIntegrationTest {


    private DoctorController ctr;
    private Repository rep;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() {
        File patient_file = null, consultation_file = null;
        try {
            patient_file = folder.newFile("patient_test.txt");
            consultation_file = folder.newFile("consultation_test.txt");
            PrintWriter o = new PrintWriter(patient_file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert patient_file != null;
        assert consultation_file != null;
        rep = new Repository(patient_file.getName(), consultation_file.getName());
        rep.cleanFile(patient_file.getName());
        rep.cleanFile(consultation_file.getName());
        ctr = new DoctorController(rep);
    }


    @Test
    public void integrationTestA() throws PatientException {
        Patient p = new Patient();
        p.setPatient_ID("1");
        p.setSSN("1111111111111");
        p.setAddress("add");
        p.setName("name");

        ctr.addPatient(p);

        assertNotEquals(-1, ctr.getPatientBySSN("1111111111111"));
    }

    @Test
    public void integrationTestAB() throws PatientException, ConsultationException {
        rep.setPatientList(new ArrayList<Patient>());
        rep.setConsultationList(new ArrayList<Consultation>());
        integrationTestA();
        List<String> meds = new ArrayList<>();
        meds.add("med1");
        meds.add("med2");
        Consultation c = new Consultation();
        c.setConsID("55");
        c.setConsultation_date("10-10-2010");
        c.setDiag("diag1");
        c.setMeds(meds);

        c.setPatientSSN("1111111111111");
        ctr.addConsultation(c);
        assertNotEquals(-1, ctr.getConsByID("55"));
    }

    @Test
    public void integrationTestABC() throws PatientException, ConsultationException {
        rep.setPatientList(new ArrayList<Patient>());
        rep.setConsultationList(new ArrayList<Consultation>());
        integrationTestA();
        integrationTestAB();

        List<Patient> temp = new ArrayList<>();
        Patient temp_p = new Patient("1111111111111", "name", "add");
        temp.add(temp_p);
        List<Patient> p;

        p = ctr.getPatientsWithDisease("diag");
        assertTrue(assertArrayEquals(temp, p));
    }

    private boolean assertArrayEquals(List<Patient> temp, List<Patient> p) {
        if (temp.size() != p.size())
            return false;
        return temp.toString().contentEquals(p.toString());

    }
}
