package labtasks.tasks;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddressBook extends Application {

    TableView<Contact> tableView;
    ObservableList<Contact> contacts;

    TextField nameField;
    TextField phoneNumberField;
    TextField emailField;

    Button addButton;
    Button editButton;
    Button deleteButton;

    @Override
    public void start(Stage primaryStage) {
        // Initialize contacts list
        contacts = FXCollections.observableArrayList();

        // Create form components
        nameField = new TextField();
        nameField.setPromptText("Name");

        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Phone Number");

        emailField = new TextField();
        emailField.setPromptText("Email");

        addButton = new Button("Add");
        addButton.setOnAction(e -> addContact());

        editButton = new Button("Edit");
        editButton.setOnAction(e -> editContact());

        deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteContact());

        // Create TableView
        tableView = new TableView<>();
        TableColumn<Contact, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Contact, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn<Contact, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableView.getColumns().addAll(nameColumn, phoneNumberColumn, emailColumn);
        tableView.setItems(contacts);

        // Create layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, new Label("Name:"), nameField);
        gridPane.addRow(1, new Label("Phone Number:"), phoneNumberField);
        gridPane.addRow(2, new Label("Email:"), emailField);
        gridPane.addRow(3, addButton, editButton, deleteButton);
        gridPane.addRow(4, tableView);

        Scene scene = new Scene(gridPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Address Book");
        primaryStage.show();
    }

    private void addContact() {
        String name = nameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();

        if (!name.isEmpty() && !phoneNumber.isEmpty() && !email.isEmpty()) {
            Contact contact = new Contact(name, phoneNumber, email);
            contacts.add(contact);
            clearFields();
        } else {
            showAlert("Error", "Please fill in all fields.");
        }
    }

    private void editContact() {
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            String newName = nameField.getText();
            String newPhoneNumber = phoneNumberField.getText();
            String newEmail = emailField.getText();

            if (!newName.isEmpty() && !newPhoneNumber.isEmpty() && !newEmail.isEmpty()) {
                selectedContact.setName(newName);
                selectedContact.setPhoneNumber(newPhoneNumber);
                selectedContact.setEmail(newEmail);
                tableView.refresh();
                clearFields();
            } else {
                showAlert("Error", "Please fill in all fields.");
            }
        } else {
            showAlert("Error", "No contact selected.");
        }
    }

    private void deleteContact() {
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            contacts.remove(selectedContact);
            clearFields();
        } else {
            showAlert("Error", "No contact selected.");
        }
    }

    private void clearFields() {
        nameField.clear();
        phoneNumberField.clear();
        emailField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class Contact {
        private String name;
        private String phoneNumber;
        private String email;

        public Contact(String name, String phoneNumber, String email) {
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
