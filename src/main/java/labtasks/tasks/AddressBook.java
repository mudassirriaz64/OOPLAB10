package labtasks.tasks;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBook extends Application {

    private static final String FILE_PATH = "contacts.txt";

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
        contacts = FXCollections.observableArrayList();
        loadContacts();

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

        tableView = new TableView<>();
        TableColumn<Contact, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Contact, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn<Contact, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableView.getColumns().addAll(nameColumn, phoneNumberColumn, emailColumn);
        tableView.setItems(contacts);

        // Set TableView width
        tableView.setMinWidth(800);
        tableView.setMaxWidth(800);

        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Label("Phone Number:"), 0, 1);
        gridPane.add(phoneNumberField, 1, 1, 2, 1); // Spanning across two columns
        gridPane.add(new Label("Email:"), 0, 2);
        gridPane.add(emailField, 1, 2, 2, 1);
        gridPane.add(addButton, 0, 3);
        gridPane.add(editButton, 1, 3);
        gridPane.add(deleteButton, 2, 3);
        gridPane.add(tableView, 0, 4, 3, 1);
        Scene scene = new Scene(gridPane, 800, 400);
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
            saveContacts();
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
                saveContacts();
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
            saveContacts();
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

    private void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    String phoneNumber = parts[1];
                    String email = parts[2];
                    contacts.add(new Contact(name, phoneNumber, email));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Contact contact : contacts) {
                writer.write(contact.getName() + "," + contact.getPhoneNumber() + "," + contact.getEmail());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
