package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Consultation;
import model.Patient;
import controller.DoctorController;
import exceptions.ConsultationException;
import exceptions.PatientException;
import validator.PatientValidation;

public class DoctorUI {
	public DoctorController ctrl;
	Scanner in;
	
	public DoctorUI(DoctorController ctrl)
	{
		this.ctrl=ctrl;
		this.in=new Scanner(System.in);
	}
	
	public DoctorController getCtrl()
	{
		return this.ctrl;
	}
	
	public Scanner getIn()
	{
		return this.in;
	}
	
	public void setCtrl(DoctorController newCtrl)
	{
		this.ctrl=newCtrl;
	}
	
	public void setIn(Scanner newIn)
	{
		this.in=newIn;
	}
	
	public void printMenu()
	{
		String menu;
		menu="PatientsManagement Menu: \n";
		menu +="\t 1 - to add a new patient; \n";
		menu +="\t 2 - to add a new consultation; \n";
		menu +="\t 3 - to list all the patients, having a certain disease; \n";
		menu +="\t 0 - exit \n";
		
		System.out.println(menu);
	}
	
	public void printMedsMenu()
	{
		String menu;
		menu="Prescriptions Menu: \n";
		menu +="\t 1 - to add a new med; \n";
		menu +="\t 2 - to close the prescription; \n";
		
		System.out.println(menu);
	}
	
	public List<String> RunMeds()
	{
		List<String> meds = new ArrayList<String>();
		printMedsMenu();
		int cmd=in.nextInt();
		in.nextLine();

		while(cmd!=2)
		{
			if(cmd==1)
			{
				System.out.println("Enter med:");
				String med = in.nextLine();
				meds.add(med);
				System.out.println(meds.toString());
			}

			printMedsMenu();
			cmd=in.nextInt();
			in.nextLine();
		}
		return meds;
	}
	
	public void Run()
	{
		printMenu();
		int cmd=in.nextInt();
		in.nextLine();
		//System.out.println(Integer.toString(c));
		while(cmd!=0)
		{
			if(cmd==1)
			{
					while(true) {
						Patient p;
						System.out.println("Enter CNP:");
						String cnp = in.nextLine();
						if (cnp != null) {
							try {
								PatientValidation.ssnValidate(cnp);
							} catch (PatientException e) {
								System.out.println("SSN not valid!");
								break;
							}
						}
						System.out.println("Enter name:");
						String name = in.nextLine();
						if (name != null) {
							try {
								PatientValidation.nameValidate(name);
							} catch (PatientException e) {
								System.out.println("Name not valid!");
								break;
							}
						}
						System.out.println("Enter address:");
						String address = in.nextLine();
						if (address != null) {
							try {
								PatientValidation.addressValidate(address);
							} catch (PatientException e) {
								System.out.println("Address not valid!");
								break;
							}
						}
						p = new Patient(cnp, name, address);
						ctrl.addPatient(p);
						break;
					}

				
			}
			if(cmd==2)
			{
				while (true) {
					System.out.println("Enter the PatientSSN:");
					String patientSSN = in.nextLine();
					//Consultation c = null;
					System.out.println("Enter the consID:");
					String consID = in.nextLine();
					System.out.println("Enter the diag:");
					String diag = in.nextLine();
					System.out.println("Introduce the prescripted meds:");
					List<String> meds = RunMeds();
					System.out.println("Date:");
					String date = in.nextLine();
//				c = new Consultation(consID, patientSSN, diag, meds, date);
					try {
						ctrl.addConsultation(consID, patientSSN, diag, meds, date);
						System.out.println("> Consultation (" + consID + ") has been successfully added.");
						break;
					} catch (ConsultationException e) {
						System.out.println("Adding consultation exception! Consultation not added! Meds are null");
					}
					break;
				}

			}
			if(cmd==3)
			{
				System.out.println("Enter the filtering disease:");
				String disease = in.nextLine();
				try {
					for (Patient p : ctrl.getPatientsWithDisease(disease)) {
						System.out.println(p.toString());
					}
				} catch (PatientException e) {
					System.out.println(e.getMessage());
				}
			}

			printMenu();
			cmd=in.nextInt();
			in.nextLine();
		}
	}
}

