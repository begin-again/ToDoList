package main;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.datamodel.ToDoData;
import main.datamodel.ToDoItem;

import java.time.LocalDate;

/**
 * Created by dad on 9/29/16.
 */
public class ControllerDialog {

  @FXML
  private TextField shortDescriptionField;
  @FXML
  private TextArea detailsArea;
  @FXML
  private DatePicker deadLinePicker;

  public ToDoItem processResults() {
    String shortDescription = shortDescriptionField.getText().trim();
    String details = detailsArea.getText().trim();
    LocalDate deadlineValue = deadLinePicker.getValue();
    ToDoItem newItem = new ToDoItem(shortDescription, details,deadlineValue);
    ToDoData.getinstance().addTodoItem(newItem);
    return newItem;
  }
}
