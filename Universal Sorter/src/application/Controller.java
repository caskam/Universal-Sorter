package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.text.rtf.*;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;
import com.sun.xml.internal.ws.util.StringUtils;

import javax.swing.text.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Controller {

	@FXML
	private TextArea textField;

	@FXML
	private Button fileButton;

	@FXML
	private Button clearButton;

	@FXML
	private Button sortButton;

	@FXML
	private Button orderButton;
	
	@FXML
	private Button warningButton;
	
	@FXML
	private Label warningLbl;
	
	@FXML
	private Pane warningPane;
	
	@FXML
	private TextField seperator;

	String currentText = "";
	Boolean ascending = true;
	File currentFile;
	boolean filePresent = false;
	
	public void maxField() {
		seperator.textProperty().addListener(
		        (observable,oldValue,newValue)-> {
		            if(newValue.length() > 1) seperator.setText(oldValue);
		        }
		);
	}

	public void warningFunc() {
		warningPane.setOpacity(0.0);
		warningButton.setOpacity(0.0);
		warningLbl.setOpacity(0.0);
		warningPane.setDisable(true);
		warningButton.setDisable(true);
		warningLbl.setDisable(true);
	}
	
	public void clearFunc() {
		textField.clear();
		fileButton.setText("OPEN");
		currentFile = null;
		filePresent = false;
	}

	public void orderFunc() {
		if (ascending) {
			orderButton.setText("DESCENDING");
			ascending = false;
		} else {
			orderButton.setText("ASCENDING");
			ascending = true;
		}
	}

	public void sortFunc() {

		String currentText = textField.getText().trim();
		StringBuilder sb = new StringBuilder();
		char sepValue = seperator.getText().charAt(0);

		for (int i = 0; i < currentText.length(); i++) {
			sb.append(currentText.charAt(i));
		}

		currentText = sb.toString();

		try {

			int matches[] = { 0, 0 };

			Pattern regex;
			Pattern regex1;

			regex = Pattern.compile("([a-zA-Z])");
			regex1 = Pattern.compile("([0-9])");

			Pattern[] regexTypes = { regex, regex1 };

			for (int i = 0; i < 2; i++) { // Determines if the content is text or numbers.
				Matcher regexMatcher = regexTypes[i].matcher(currentText);
				while (regexMatcher.find()) {
					for (int j = 1; j <= regexMatcher.groupCount(); j++) {
						if (regexMatcher.group(i) != null) {
							matches[i] += 1;
						}
					}
				}
			}

			if (matches[0] > matches[1]) { // Alphabetical sort

				String tempS = "";
				List<String> sortedText = new ArrayList<>();
				StringBuilder sb2 = new StringBuilder();
				
				for (int i = 0; i < currentText.length(); i++) {
					if (currentText.charAt(i) == sepValue) {
						tempS = sb2.toString().trim();
						if (!tempS.isEmpty()) {
							if (Pattern.matches(".*[a-zA-Z]+.*", tempS)) {
								sortedText.add(tempS);
							}
						}
						sb2.setLength(0);
					} else {
						sb2.append(currentText.charAt(i));
					}
				}
				
				tempS = sb2.toString().trim();
				if (!tempS.isEmpty()) {
					if (!tempS.matches("[0-9]+")) {
						sortedText.add(tempS);
					}
				}
				
				java.util.Collections.sort(sortedText, String.CASE_INSENSITIVE_ORDER);
				if (ascending == false) {
					java.util.Collections.reverse(sortedText);
				}
				
				sb2.setLength(0);
				for(int i = 0; i < sortedText.size(); i++) {
					sb2.append(sortedText.get(i).toString().trim() + sepValue + " ");
				}
				sb2.deleteCharAt(sb2.length() - 1);
				
				if (sb2.charAt(0) == ',') {
					sb2.deleteCharAt(0);
				}
				if (sb2.charAt(sb2.length()-1) == ',') {
					sb2.deleteCharAt(sb2.length()-1);
				}
				if (sb2.charAt(0) == ' ') {
					sb2.deleteCharAt(0);
				}
				
				textField.setText(sb2.toString());
				
			} else if (matches[0] < matches[1]) { // Numerical sort
				
				System.out.println("numbers");
				List<Double> sortedNumbers = new ArrayList<>();
				String tempS = "";
				Boolean decimal = false;
				StringBuilder sb2 = new StringBuilder();
				
				for (int i = 0; i < currentText.length(); i++) {
					if (currentText.charAt(i) == sepValue) {
						tempS = sb2.toString().trim();
						if (tempS.matches("^[0-9]\\d*(\\.\\d+)?$")) {
							System.out.println("Added" + tempS);
							if(tempS.contains(".")) {
								decimal = true;
							}
							sortedNumbers.add(Double.parseDouble(tempS));
						}
						sb2.setLength(0);
					} else {
						sb2.append(currentText.charAt(i));
					}
				}
				
				tempS = sb2.toString().trim();
				if (tempS.matches("^[0-9]\\d*(\\.\\d+)?$")) {
					System.out.println(tempS);
					if(tempS.contains(".")) {
						decimal = true;
					}
					sortedNumbers.add(Double.parseDouble(tempS));
				}
				
				sb2.setLength(0);
				java.util.Collections.sort(sortedNumbers);
				if (ascending == false) {
					java.util.Collections.reverse(sortedNumbers);
				}
				
				sb2.setLength(0);
				for(int i = 0; i < sortedNumbers.size(); i++) {
					sb2.append(sortedNumbers.get(i).toString().trim() + sepValue + " ");
				}
				sb2.deleteCharAt(sb2.length() - 1);
				
				if (sb2.charAt(0) == ',') {
					sb2.deleteCharAt(0);
				}
				if (sb2.charAt(sb2.length()-1) == ',') {
					sb2.deleteCharAt(sb2.length()-1);
				}
				if (sb2.charAt(0) == ' ') {
					sb2.deleteCharAt(0);
				}
				
				tempS = sb2.toString();
				System.out.println(tempS);
				if(decimal == false) {
				tempS = tempS.replaceAll("([.][0]+)", ""); // If no decimals found, integers are returned.
				} 
				System.out.println(tempS);
				textField.setText(tempS);
				
			} else { // No sortable content has been found, report to the user

				warningPane.setOpacity(1.0);
				warningButton.setOpacity(1.0);
				warningLbl.setOpacity(1.0);
				warningPane.setDisable(false);
				warningButton.setDisable(false);
				warningLbl.setDisable(false);
			}

		} catch (PatternSyntaxException ex) {
			System.out.println("Syntax error");
		}

	}

	public void fileBoolFunc() {
		if (textField.getText().isEmpty()) {
			fileButton.setText("OPEN");
		} else {
			if (filePresent == true) {
				fileButton.setText("OVERWRITE FILE");
			} else {
				fileButton.setText("SAVE AS");
			}
		}

	}

	public void fileFuncs() throws IOException, BadLocationException {
		if (filePresent) {
			saveFile();
		} else {
			openFile();
		}
	}

	private void SaveFileAction(String content, File file) {
		try {

			BufferedWriter out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()), "UTF-8"));
			try {
				String ls = System.getProperty("line.separator");
				String text = content;
				content.replaceAll("\n", ls);
				out.write(text);
			} finally {
				out.close();
			}

		} catch (IOException ex) {
			System.out.println("save error");
		}

	}

	public void saveFile() throws IOException {
		if (filePresent == true) { // save

			BufferedWriter out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(currentFile.getAbsolutePath()), "UTF-8"));
			try {
				String ls = System.getProperty("line.separator");
				String text = textField.getText().replaceAll("\n", ls);
				out.write(text);
			} finally {
				out.close();
			}

		} else { // save as

			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog(textField.getScene().getWindow());

			if (file != null) {
				SaveFileAction(textField.getText(), file);
			}
		}
	}

	public void menuSaveFunc() throws IOException {
		saveFile();
	}

	public void menuSaveAsFunc() {

		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog(textField.getScene().getWindow());

		if (file != null) {
			SaveFileAction(textField.getText(), file);
		}
	}

	private String getFileExtension(File file) {
		String name = file.getName();
		try {
			return name.substring(name.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "";
		}
	}

	public void openFile() throws IOException, BadLocationException {
		textField.clear();

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt", "*.rtf"));
		currentFile = fileChooser.showOpenDialog(textField.getScene().getWindow());

		if (getFileExtension(currentFile).equals("rtf")) {
			System.out.println("true");
			System.out.println("rtf here");
			RTFEditorKit rtf = new RTFEditorKit();
			Document doc = rtf.createDefaultDocument();

			FileInputStream fis = new FileInputStream(currentFile.getAbsolutePath());
			rtf.read(fis, doc, 0);
			textField.setText(doc.getText(0, doc.getLength()));

			fileButton.setText("SAVE AS");
			filePresent = false;
		} else {

			StringBuilder builder = new StringBuilder();
			BufferedReader in = new BufferedReader(
					new InputStreamReader(new FileInputStream(currentFile.getAbsolutePath()), "UTF-8"));
			String ls = System.getProperty("line.separator");

			try {
				String str = "";
				while ((str = in.readLine()) != null) {
					builder.append(str + ls);
				}

				builder.setLength(builder.length() - 1);

				textField.setText(builder.toString());

			} finally {
				in.close();
			}

			fileButton.setText("OVERWRITE FILE");
			filePresent = true;
		}

		int check = (int) textField.getText().charAt(0);
		if (check > 127) {
			textField.setEditable(false);
		}

	}

	public void menuOpenFunc() throws IOException, BadLocationException {
		openFile();
	}

}
