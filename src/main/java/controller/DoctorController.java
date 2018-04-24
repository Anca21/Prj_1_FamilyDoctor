package controller;

import exceptions.ConsultationException;
import exceptions.PatientException;
import model.Consultation;
import model.Patient;
import repository.Repository;
import validator.ConsultationValidation;
import validator.PatientValidation;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DoctorController {

	private Repository rep;

	/** Constructors */

	public DoctorController(Repository rep) {
		this.rep = rep;
		// Get list from file in order to avoid duplicates.
	}

	/** Getters */
	private List<Patient> getPatientList() {
		return rep.getPatientList();
	}

	private List<Consultation> getConsultationList() {
		return rep.getConsultationList();
	}

	public void setConsulationList(List<Consultation> consultationList) {
		rep.setConsultationList(consultationList);
	}

	public int getPatientBySSN(String SSN) {
	    List<Patient> temp = rep.getPatientsFromFile();
		for (int i = 0; i < temp.size(); i++) {
			if (Objects.equals(temp.get(i).getSSN(), SSN))
				return i;
		}

		return -1;
	}

	public int getConsByID(String ID) {
	    List<Consultation> temp = rep.getConsultationsFromFile();
		for (int i = 0; i < temp.size(); i++) {
			if (Objects.equals(temp.get(i).getConsID(), ID)) {
				return i;
			}
		}
		return -1;
	}

	/** Others */
	public void addPatient(Patient p) throws PatientException {

		List<Patient> temp = rep.getPatientList();
		PatientValidation.ssnValidate(p.getSSN());
		PatientValidation.addressValidate(p.getAddress());
		PatientValidation.nameValidate(p.getName());
		temp.add(p);
		rep.setPatientList(temp);
		try {
			rep.savePatientToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// adding of a new consultation for a patient (consultation date,
	// diagnostic, prescription drugs)

	public void addConsultation(Consultation c) throws ConsultationException {
		if (c.getMeds() == null || c.getMeds().size()==0) {
            System.out.println("Meds list is empty!");
            throw new ConsultationException("Meds list is empty!");
        }
		if (getPatientBySSN(c.getPatientSSN())==-1) {
            System.out.println("Patient not in the system!");
            throw new ConsultationException("Patient not in the system!");
        }
		ConsultationValidation.consIdValidation(c.getConsID());
        ConsultationValidation.dateValidate(c.getConsultation_date());
		List<Consultation> temp = rep.getConsultationList();
		temp.add(c);
		rep.setConsultationList(temp);
		try {
				rep.saveConsultationToFile();
		} catch (IOException e) {
				e.printStackTrace(); }
	}

	public List<Patient> getPatientsWithDisease(String disease) throws PatientException {
		List<Consultation> c = this.getConsultationList();
		List<Patient> p = this.getPatientList();
		if (disease != null) {
			if (disease.length() == 0) {
				throw new PatientException("Empty disease provided");
			}
			int chk = 1;

			for (Consultation aC : c) {
				if (aC.getDiag().toLowerCase()
						.contains(disease.toLowerCase())) // so that it is case
				// insensitive
				{
					for (Patient aP : p) {
						if (aP.getSSN().equals(aC.getPatientSSN())) {
							chk = aP.getConsNum();
						}
					}

					if (chk == 1) {
						if(this.getPatientBySSN(aC.getPatientSSN())!=-1) {
							Patient e = this.getPatientList().get(this.getPatientBySSN(aC.getPatientSSN()));
							p.add(e); // get
							// Patient
							// by
							// SSN
						}
						else {
							throw new PatientException("There is no patient with this disease");
						}
					}
					chk = 1;
				}
			}

			// Sort the list

			Patient paux = new Patient();

			for (int i = 0; i < p.size(); i++)
				for (int j = i + 1; j < p.size() - 1; j++)
					if (p.get(j - 1).getConsNum() < p.get(j).getConsNum()) {
						paux = p.get(j - 1);
						p.set(j - 1, p.get(j));
						p.set(j, paux);
					}
		}
		else {
			throw new PatientException("Null disease parameter provided");
		}
		return p;
	}

	/*
	 * For debugging purposes public void printList() { for (int i = 0; i <
	 * PatientList.size(); i++) {
	 * System.out.println(PatientList.get(i).toString()); } }
	 */

}
