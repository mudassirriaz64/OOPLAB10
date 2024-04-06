package labtasks.tasks;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StudentRegistrationForm extends Application {

    TableView<Student> tableView;

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Labels
        Label nameLabel = new Label("Name:");
        Label idLabel = new Label("ID:");
        Label emailLabel = new Label("Email:");
        Label genderLabel = new Label("Gender:");
        Label termsLabel = new Label("I agree to terms and conditions:");

        // TextFields
        TextField nameField = new TextField();
        TextField idField = new TextField();
        TextField emailField = new TextField();

        // Radio Buttons for Gender
        ToggleGroup genderToggle = new ToggleGroup();
        RadioButton maleRadio = new RadioButton("Male");
        maleRadio.setToggleGroup(genderToggle);
        RadioButton femaleRadio = new RadioButton("Female");
        femaleRadio.setToggleGroup(genderToggle);

        // Checkbox for Terms and Conditions
        CheckBox termsCheckbox = new CheckBox();

        // Button to Submit
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            String id = idField.getText();
            String email = emailField.getText();
            String gender = "";
            if (maleRadio.isSelected()) {
                gender = "Male";
            } else if (femaleRadio.isSelected()) {
                gender = "Female";
            }
            boolean termsAgreed = termsCheckbox.isSelected();

            // Add student to table view
            tableView.getItems().add(new Student(name, id, email, gender, termsAgreed));

            // Clear form fields after submission
            nameField.clear();
            idField.clear();
            emailField.clear();
            genderToggle.selectToggle(null);
            termsCheckbox.setSelected(false);
        });

        // TableView
        tableView = new TableView<>();
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Student, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Student, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Student, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        TableColumn<Student, Boolean> termsColumn = new TableColumn<>("Terms Agreed");
        termsColumn.setCellValueFactory(new PropertyValueFactory<>("termsAgreed"));

        tableView.getColumns().addAll(nameColumn, idColumn, emailColumn, genderColumn, termsColumn);

        // Add components to grid pane
        gridPane.addRow(0, nameLabel, nameField);
        gridPane.addRow(1, idLabel, idField);
        gridPane.addRow(2, emailLabel, emailField);
        gridPane.addRow(3, genderLabel, maleRadio, femaleRadio);
        gridPane.addRow(4, termsLabel, termsCheckbox);
        gridPane.addRow(5, submitButton);
        gridPane.addRow(6, new Label("Registered Students:"), tableView);

        Scene scene = new Scene(gridPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Student Registration Form");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class Student {
        private String name;
        private String id;
        private String email;
        private String gender;
        private boolean termsAgreed;

        public Student(String name, String id, String email, String gender, boolean termsAgreed) {
            this.name = name;
            this.id = id;
            this.email = email;
            this.gender = gender;
            this.termsAgreed = termsAgreed;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getGender() {
            return gender;
        }

        public boolean isTermsAgreed() {
            return termsAgreed;
        }
    }
}
