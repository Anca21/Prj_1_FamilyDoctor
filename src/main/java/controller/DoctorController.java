package controller;

import exceptions.ConsultationException;
import exceptions.PatientException;
import model.Consultation;
import model.Patient;
import repository.Repository;
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

	private int getPatientBySSN(String SSN) {
		for (int i = 0; i < rep.getPatientList().size(); i++) {
			if (Objects.equals(rep.getPatientList().get(i).getSSN(), SSN))
				return i;
		}

		return -1;
	}

	public int getConsByID(String ID) {
		for (int i = 0; i < rep.getConsultationList().size(); i++) {
			if (rep.getConsultationList().get(i).getConsID().compareTo(ID) == 0) {
				/*
				 * System.out.println("I proud to have found " + ID + " here: "
				 * + i); System.out.println("Proof : " +
				 * ConsultationList.get(i).toString());
				 */
				return i - 1;
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
		if (c.getMeds() == null)
			throw new ConsultationException("meds is null");
			List<Consultation> temp = rep.getConsultationList();
			temp.add(c);
			rep.setConsultationList(temp);
			try {
				rep.saveConsultationToFile(c);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
