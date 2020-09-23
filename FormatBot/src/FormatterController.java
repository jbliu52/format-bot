/**
 * @author jerryliu
 * Date: Sep 19, 2020
 * Time spent:
 * 
 * 
 */

public class FormatterController {

	public static String format(String input) {
		return input.replaceAll("\\{&&(^\\{\\$)", "{\n").replaceAll("\\.+\\}\\.+", "\n}");
	}
	
	public static String formatIf(String input) {
		return input.replaceAll("if\\s*?\\(", "if (").replaceAll("\\)\\s*?\\{", ") {");
	}
	
	public static String formatElse(String input) {
		return input.replaceAll("\\}\\s*?else", "} else").replaceAll("else\\s*?\\{", "else {");
	}
	
	public static String formatFor(String input) {
		return input.replaceAll("for\\s*?\\(", "for (").replaceAll("\\s*?;\\s*?", " ; ").replaceAll(")\\s*?{", ") {");
	}

	public static String formatWhile(String input) {
		return "";
	}

	public static String formatSwitch(String input) {
		return "";
	}
}
