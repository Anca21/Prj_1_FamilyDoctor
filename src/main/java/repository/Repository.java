package repository;


import model.Consultation;
import model.Patient;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Repository {

    private String patientsFile; // patientsFile file
    private String consultationsFile; // consultationsFile file

    public List<Patient> getPatientList() {
        return patientList;
    }

    public List<Consultation> getConsultationList() {
        return consultationList;
    }

    private List<Patient> patientList = new ArrayList<>();
    private List<Consultation> consultationList = new ArrayList<>();

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    public void setConsultationList(List<Consultation> consultationList) {
        this.consultationList = consultationList;
    }



    public Repository(String patientsFile, String consultationsFile) {
        this.patientsFile = patientsFile;
        this.consultationsFile = consultationsFile;
    }

    public void cleanFile(String file) {
        FileWriter fw;
        try {
            fw = new FileWriter(file);
            PrintWriter out = new PrintWriter(fw);
            out.print("");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String[] getFromFile(String file) throws IOException {
        int n = 0;
        BufferedReader in = new BufferedReader(new FileReader(file));
        while ((in.readLine()) != null) {
            n++;
        }
        in.close();

        String[] la = new String[n];
        String s;
        int i = 0;
        in = new BufferedReader(new FileReader(file));
        while ((s = in.readLine()) != null) {
            la[i] = s;
            i++;
        }
        in.close();
        return la;
    }

    public List<Patient> getPatientsFromFile() {
        List<Patient> lp = new ArrayList<>();
        try {
            String[] tokens = getFromFile(patientsFile);

            String tok;
            String[] pat;
            int i = 0;
            while (i < tokens.length) {
                tok = tokens[i];
                pat = tok.split(",");
                lp.add(new Patient(pat[0], pat[1], pat[2]));
                i = i + 1;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lp;
    }

    public List<Consultation> getConsultationsFromFile() {
        List<Consultation> lp = new ArrayList<>();
        try {
            String[] tokens = getFromFile(consultationsFile);


            String tok;
            String[] cons;
            String[] meds;
            List<String> med = new ArrayList<>();
            int i = 0;
            while (i < tokens.length) {
                tok = tokens[i];
                cons = tok.split(",");
                meds = cons[3].split("\\+");
                Consultation consultation = new Consultation(cons[0], cons[1], cons[2], med, cons[4]);
                for (int j = 0; j < meds.length - 1; j++) {
                    consultation.getMeds().add(meds[j]);
                }
                lp.add(consultation);
                i = i + 1;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lp;
    }

    public void savePatientToFile() throws IOException        // save to file
    {
        FileOutputStream f = new FileOutputStream(new File(patientsFile));
        PrintWriter o = new PrintWriter(f);

        for (Patient p: getPatientList())
            o.println(p.toString());
        o.close();
//        int n = 0;
//        BufferedReader in = new BufferedReader(new FileReader(patientsFile));
//        while ((in.readLine()) != null)
//            n++;
//        in.close();
//        String[] sl = new String[n];
//        String str;
//        int i = 0;
//        in = new BufferedReader(new FileReader(patientsFile));
//        while ((str = in.readLine()) != null) {
//            sl[i] = str;
//            i++;
//        }
//        in.close(); // append
//        FileWriter fw = new FileWriter(patientsFile);
//        PrintWriter out = new PrintWriter(fw);
//        for (i = 1; i < sl.length - 1; i++)
//            out.println(sl[i]);
//        out.println(p.toString());
//        out.close();
    }

    public void saveConsultationToFile(Consultation c) throws IOException        // save to file
    {
        int n = 0;
        BufferedReader in = new BufferedReader(new FileReader(consultationsFile));
        while ((in.readLine()) != null)
            n++;
        in.close();
        String[] sl = new String[n];
        String str;
        int i = 0;
        in = new BufferedReader(new FileReader(consultationsFile));
        while ((str = in.readLine()) != null) {
            sl[i] = str;
            i++;
        }
        in.close(); // append
        FileWriter fw = new FileWriter(consultationsFile);
        PrintWriter out = new PrintWriter(fw);
        for (i = 0; i < sl.length - 1; i++)
            out.println(sl[i]);
        out.println(c.toString());
        out.close();
    }
}
