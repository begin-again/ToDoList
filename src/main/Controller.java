package main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import main.datamodel.ToDoItem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {
  private List<ToDoItem> todoItems;
  @FXML
  private ListView<ToDoItem> todoListView;
  @FXML
  private TextArea itemDetailsTextArea;
  @FXML
  private Label deadlineLabel;

  public void initialize() {
    // sample data to test the ui
    ToDoItem item1 = new ToDoItem("Samle Task1", "Do something interesting1", LocalDate.now().plusDays(-1));
    ToDoItem item2 = new ToDoItem("Samle Task2", "Do something interesting2", LocalDate.now().plusDays(1));
    ToDoItem item3 = new ToDoItem("Samle Task3", "Do something interesting3", LocalDate.now().plusDays(2));
    ToDoItem item4 = new ToDoItem("Samle Task4", "Do something interesting4", LocalDate.now().plusDays(3));
    ToDoItem item5 = new ToDoItem("Samle Task5", "Do something interesting5", LocalDate.now().plusMonths(1));

    todoItems = new ArrayList<ToDoItem>();
    todoItems.add(item1);
    todoItems.add(item2);
    todoItems.add(item3);
    todoItems.add(item4);
    todoItems.add(item5);

    todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItem>() {
      @Override
      public void changed(ObservableValue<? extends ToDoItem> observable, ToDoItem oldValue, ToDoItem newValue) {
        if (newValue != null) {
          ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
          itemDetailsTextArea.setText(item.getDetails());
          DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
          deadlineLabel.setText(df.format(item.getDeadLine()));
        }
      }
    });
    todoListView.getItems().setAll(todoItems);
    todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    todoListView.getSelectionModel().selectFirst();

  }

  @FXML
  public void handleClickListView() {
    ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
    itemDetailsTextArea.setText(item.getDetails());
    deadlineLabel.setText(item.getDeadLine().toString());
  }
}
