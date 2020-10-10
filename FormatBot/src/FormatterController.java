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
		
		return "" + (tabs + spaces /4) + " " + input.substring(tabs + spaces - spaces % 4);
	}
	
	public static String unsqueeze(String input) {
		String output = "";
		String[] lines = input.split("\n");
		
		for(String line : lines) {
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
		input = input.substring(s.length() + 1); //.replaceAll("\\s*\\{", " {");
		String str = "";
		
		int last = input.length() - 1;
		for(; last > input.lastIndexOf('{'); last--) {
			if(!Character.isWhitespace(input.charAt(last))) {
				break;
			}
		}
		
		boolean comment = false;
		boolean string = false;
		
		for(int i = 0; i < input.length(); i++) {
			if(input.charAt(i) == '/' && i + 1 < input.length() && input.charAt(i + 1) == '/') {
				str += input.substring(i);
				break;
			}
			if(input.charAt(i) == '/' && i + 1 < input.length() && input.charAt(i + 1) == '*') {
				comment = true;
			}
			if(i - 1 > 0 && input.charAt(i - 1) == '*' && input.charAt(i) == '/') {
				comment = false;
			}
			if(input.charAt(i) == '\"' && !comment) {
				string = !string;
			}
			if(!string && !comment) {
				if(input.charAt(i) == '{' && i < last) {
					if(i > 0 && !Character.isWhitespace(input.charAt(i - 1))) {
						str += " ";
					}
					str += "{\n" + (tabs + 1) + " ";
				}else if(input.charAt(i) == '}' && i > 0) {
					str += "\n" + tabs + " }";
				}else {
					str += input.charAt(i);
				}
			}else {
				str += input.charAt(i);
			}
		}
		
		return s + " " + str;
	}
	
	public static String formatStatements(String input) {
		//System.out.println(input + " ee");
		if(input.contains("if")) {
			input = FormatterController.formatIf(input);
		}
		if(input.contains("else")) {
			input = FormatterController.formatElse(input);
		}
		if(input.contains("for")) {
			input = FormatterController.formatFor(input);
		}
		return input;
	}
	
	private static String formatIf(String input) {
		return input.replaceAll("if\\s*\\(", "if ("); //.replaceAll("\\)\\s*\\{", ") {");
	}
	
	private static String formatElse(String input) {
		return input.replaceAll("\\}\\s*else", "} else"); //.replaceAll("else\\s*\\{", "else {");
	}
	
	private static String formatFor(String input) {
		return input.replaceAll("for\\s*\\(", "for (").replaceAll("\\s*;\\s*", " ; ");
	}

	public static String formatWhile(String input) {
		return input.replaceAll("while\\s*\\(", "while (");
	}

	public static String formatSwitch(String input) {
		return input.replaceAll("switch\\s*\\(", "while (");
	}
	
//	private void setPrefs() {
//		
//	}
}
