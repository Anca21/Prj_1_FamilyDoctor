package validator;

import exceptions.ConsultationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsultationValidation {
	public static void consIdValidation(String id) throws  ConsultationException {
		if (id.length() == 0) {
			System.out.println("One of the required fields is empty!");
			throw new ConsultationException("One of the required fields is empty!");
		}
		Pattern pattern = Pattern.compile("^[0-9]+$");
		Matcher matcher = pattern.matcher(id);
		if (!matcher.find()) {
			System.out.println("The \"id\" field has an invalid format!");
			throw new ConsultationException("The \"id\" field has an invalid format!");
		}
	}
	
	public static void dateValidate(String date) throws ConsultationException {
		if (date.length() != 10) {
			System.out.println("Invalid date!");
			throw new ConsultationException("Incorrect date!");
		}
		Pattern pattern = Pattern.compile("^[0-9]{2}-[0-9]{2}-[0-9]{4}$");
		Matcher matcher = pattern.matcher(date);
		if (!matcher.find()) {
			System.out.println("The \"date\" field has an invalid format!");
			throw new ConsultationException("The \"date\" field has an invalid format!");
		}
	}

}
