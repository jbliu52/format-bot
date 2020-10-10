import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * @author jerryliu
 * @version Sep 17, 2020
 * 
 * 
 */

public class FormatterGUI extends Application{
	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;
	
	private Stage stage;
	private Stack<File> undo;
	private Stack<String> paths;
	private CheckBox sameFile;
	
	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		undo = new Stack<File>();
		paths = new Stack<String>();
		
		title();
	}
	
	public void title() {
		VBox root = new VBox();
		root.setAlignment(Pos.TOP_CENTER);
		Scene titleScreen = new Scene(root, WIDTH, HEIGHT);
		
		MenuBar menu = new MenuBar();
		Menu file = new Menu("File");
		MenuItem settings = new MenuItem("Settings");
		MenuItem quit = new MenuItem("Quit");
		file.getItems().addAll(settings, quit);
		menu.getMenus().add(file);

		VBox main = new VBox(20); 
		main.setAlignment(Pos.CENTER);
		main.setPrefHeight(HEIGHT - menu.getHeight());
		
		Text title = new Text("CS Auto-Formatinator 18000");
		title.setFont(new Font(30));
		title.setTextAlignment(TextAlignment.CENTER);
		
		Button format = new Button("Format!");
		format.setFont(new Font(20));
		
		Button undo = new Button("Undo!");
		format.setFont(new Font(20));
		
		sameFile = new CheckBox("Write to different file");
		
		main.getChildren().addAll(title, format);
		if(!paths.isEmpty() && !this.undo.isEmpty()) {
			main.getChildren().add(undo);
		}
		main.getChildren().add(sameFile);
		
		root.getChildren().addAll(menu, main);
		
		format.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				fileSelect();
			}
		});
		
		undo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				undo();
			}
		});
		
		quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		
		stage.setScene(titleScreen);
		stage.show();
	}
	
	public void fileSelect() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File to Format");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Java Files", "*.java"));
		File file = fileChooser.showOpenDialog(stage);
		if(file != null) {
			if(!sameFile.isSelected()) {
				undo.push(copy(file));
				paths.push(file.getPath());
				format(file, file);
			}else {
				File output = fileChooser.showOpenDialog(stage);
				if(output != null) {
					undo.push(copy(output));
					paths.push(output.getPath());
					format(file, output);
				}
			}
		}
		title();
	}
	
	public File copy(File file) {
		File copy = new File(file.getName());
		Scanner reader;
		FileWriter writer;
		try {
			reader = new Scanner(file);
			writer = new FileWriter(copy);
			while(reader.hasNextLine()) {
				writer.write(reader.nextLine() + '\n');
			}
			reader.close();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		copy.deleteOnExit();
		return copy;
	}

	public void undo() {
		String filepath = paths.pop();
		File temp = undo.pop();
		File copy = new File(filepath);
		Scanner reader;
		FileWriter writer;
		try {
			reader = new Scanner(temp);
			writer = new FileWriter(copy);
			while(reader.hasNextLine()) {
				writer.write(reader.nextLine() + '\n');
			}
			reader.close();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		title();
	}
	
	public void format(File file, File output) {
		Scanner reader;
		FileWriter writer;
		try {
			reader = new Scanner(file);
			String out = "";
			boolean string = false;
			boolean comment = false;
			boolean line = false;
			while(reader.hasNextLine()) {
				String str = reader.nextLine();
				str = FormatterController.squeeze(str);
				
				String brug = "";
				String s = "";
				for(int i = 0; i < str.length(); i++) {
					if(str.charAt(i) == '/' && i + 1 < str.length() && str.charAt(i + 1) == '/') {
						brug += FormatterController.formatStatements(s);
						brug += str.substring(i);
						line = true;
						break;
					}
					if(str.charAt(i) == '/' && i + 1 < str.length() && str.charAt(i + 1) == '*') {
						comment = true;
						brug += FormatterController.formatStatements(s);
						s = "";
					}
					if(i - 1 > 0 && str.charAt(i - 1) == '*' && str.charAt(i) == '/') {
						comment = false;
					}
					if(str.charAt(i) == '\"' && !comment) {
						string = !string;
						if(string) {
							brug += FormatterController.formatStatements(s);
							s = "";
						}
					}
					if(!string && !comment) {
						s += str.charAt(i);
					}else {
						brug += str.charAt(i);
					}
				}
				if(!line) {
					brug += FormatterController.formatStatements(s);
				}else {
					line = false;
				}
				if(!string && !comment) {
					brug = FormatterController.format(brug);
				}
				out += FormatterController.unsqueeze(brug);
			}
			writer = new FileWriter(output);
			writer.write(out);
			reader.close();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void settings() {
		
	}
}
