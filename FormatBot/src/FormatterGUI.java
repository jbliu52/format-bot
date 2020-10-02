import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	
	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
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
		
		main.getChildren().addAll(title, format);
		
		root.getChildren().addAll(menu, main);
		
		format.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				fileSelect();
			}
		});
		
		quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
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
		List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);
		for(File file : selectedFiles) {
			if(file != null) {
				System.out.println(file.toPath());
				File output = fileChooser.showOpenDialog(stage);
				format(file, output);
			}
		}
	}
	
	public void format(File file, File output) {
		Scanner reader;
		FileWriter writer;
		try {
			reader = new Scanner(file);
			writer = new FileWriter(output);
			while(reader.hasNextLine()) {
				String str = reader.nextLine();
				str = FormatterController.squeeze(str);
				if(str.contains("if")) {
					str = FormatterController.formatIf(str);
				}
				if(str.contains("else")) {
					str = FormatterController.formatElse(str);
				}
				str = FormatterController.format(str);
				//System.out.println(str);
				writer.write(FormatterController.unsqueeze(str));
			}
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
