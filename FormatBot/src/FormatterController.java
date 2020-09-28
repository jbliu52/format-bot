/**
 * @author jerryliu
 * Date: Sep 19, 2020
 * Time spent:
 * 
 * 
 */

public class FormatterController {

	public static String squeeze(String input) {
		int tabs = 0;
		int spaces = 0;
		for(int i = 0; i < input.length(); i++) {
			if(input.charAt(i) == '\t') {
				tabs++;
			}else if(input.charAt(i) == ' ') {
				spaces++;
			}else if(!Character.isWhitespace(input.charAt(i))) {
				break;
			}
		}
		return "" + (tabs + spaces /4) + " " + input.substring(tabs + spaces);
	}
	
	public static String unsqueeze(String input) {
		String output = "";
		String[] str = input.split(" ");
		int tabs = Integer.valueOf(str[0]);
		for(int i = 0; i < tabs; i++) {
			output += "\t";
		}
		return output + input.substring(str[0].length() + 1);
	}
	
	public static String format(String input) {
		int tabs = Integer.valueOf(input.split(" ")[0]);
		input = input.replaceAll("\\s*?\\{", " {").replace("\\s+?$", "");
		String str = "";
		for(int i = 0; i < input.length() - 1; i++) {
			if(input.charAt(i) == '{') {
				str += "{\n" + (tabs + 1) + " ";
			}else if(input.charAt(i) == '}') {
				str += "\n" + tabs + " }";
			}else {
				str += input.charAt(i);
			}
		}
		return str + input.charAt(input.length() - 1);
	}
	
	public static String formatIf(String input) {
		return input.replaceAll("if\\s*?\\(", "if ("); //.replaceAll("\\)\\s*?\\{", ") {");
	}
	
	public static String formatElse(String input) {
		return input.replaceAll("\\}\\s*?else", "} else"); //.replaceAll("else\\s*?\\{", "else {");
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
	
//	private void setPrefs() {
//		
//	}
}
