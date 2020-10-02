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
		String[] lines = input.split("\n");
		
		for(String line : lines) {
			System.out.println(line);
			String[] str = line.split(" ");
			int tabs = Integer.valueOf(str[0]);
			for(int i = 0; i < tabs; i++) {
				output += "\t";
			}
			output += line.substring(str[0].length() + 1) + "\n";
		}
		
		return output;
	}
	
	public static String format(String input) {
		String s = input.split(" ")[0];
		int tabs = Integer.valueOf(s);
		input = input.replaceAll("\\s*\\{", " {").substring(s.length() + 1);
		String str = "";
		
		int last = input.length() - 1;
		for(; last > input.lastIndexOf('{'); last--) {
			if(!Character.isWhitespace(input.charAt(last))) {
				break;
			}
		}
		
		for(int i = 0; i < input.length(); i++) {
			if(input.charAt(i) == '{' && i < last) {
				str += "{\n" + (tabs + 1) + " ";
			}else if(input.charAt(i) == '}' && i > 0) {
				str += "\n" + tabs + " }";
			}else {
				str += input.charAt(i);
			}
		}
		
		return s + " " + str;
	}
	
	public static String formatIf(String input) {
		return input.replaceAll("if\\s*\\(", "if ("); //.replaceAll("\\)\\s*\\{", ") {");
	}
	
	public static String formatElse(String input) {
		return input.replaceAll("\\}\\s*else", "} else"); //.replaceAll("else\\s*\\{", "else {");
	}
	
	public static String formatFor(String input) {
		return input.replaceAll("for\\s*\\(", "for (").replaceAll("\\s*;\\s*", " ; ").replaceAll(")\\s*{", ") {");
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
