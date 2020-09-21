/**
 * @author jerryliu
 * Date: Sep 19, 2020
 * Time spent:
 * 
 * 
 */

public class FormatterController {

	public static String format(String input) {
		return "";
	}
	
	public static String formatIf(String input) {
		return input.replaceAll("if\\(", "if (").replaceAll("\\)\\{", ") {");
	}
	
	public static String formatElse(String input) {
		return input.replaceAll("\\}else", "} else").replaceAll("else\\{", "else {");
	}
	
	public static String formatFor(String input) {
		return "";
	}

	public static String formatWhile(String input) {
		return "";
	}

	public static String formatSwitch(String input) {
		return "";
	}
}
