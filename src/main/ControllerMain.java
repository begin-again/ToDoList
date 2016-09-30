package main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import main.datamodel.ToDoData;
import main.datamodel.ToDoItem;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ControllerMain {
  private List<ToDoItem> todoItems;
  @FXML
  private ListView<ToDoItem> todoListView;
  @FXML
  private TextArea itemDetailsTextArea;
  @FXML
  private Label deadlineLabel;
  @FXML
  private BorderPane mainBorderPane;

  public void initialize() {

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
    todoListView.setItems(ToDoData.getinstance().getTodoItems());
    todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    todoListView.getSelectionModel().selectFirst();

    todoListView.setCellFactory(new Callback<ListView<ToDoItem>, ListCell<ToDoItem>>() {
      @Override
      public ListCell<ToDoItem> call(ListView<ToDoItem> param) {
        ListCell<ToDoItem> cell = new ListCell<ToDoItem>(){
          @Override
          protected void updateItem(ToDoItem item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
              setText(null);
            } else {
              setText(item.getShortDescription());
              if(item.getDeadLine().isBefore(LocalDate.now().plusDays(1)) ) {
                setTextFill(Color.DARKRED);
              } else {
                if (item.getDeadLine().equals(LocalDate.now().plusDays(1)) ) {
                  setTextFill(Color.BROWN);

                }
              }
            }
          }
        };
        return cell;
      }
    });
  }

  @FXML
  public void showNewItemDialog() {
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.initOwner(mainBorderPane.getScene().getWindow());
    dialog.setTitle("Add New ToDo Item");
    dialog.setHeaderText("Use this dialog to create a new ToDo Item");
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
    try {
      dialog.getDialogPane().setContent(fxmlLoader.load());
    } catch (IOException e) {
      System.out.println("Couldn't load the dialog");
      e.printStackTrace();
      return;
    }

    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

    Optional<ButtonType> result = dialog.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {
      ControllerDialog controller  = fxmlLoader.getController();
      ToDoItem newItem =  controller.processResults();
      todoListView.getSelectionModel().select(newItem);
    }

  }

  @FXML
  public void handleClickListView() {
    ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
    itemDetailsTextArea.setText(item.getDetails());
    deadlineLabel.setText(item.getDeadLine().toString());
  }
}
