module labtasks.tasks {
    requires javafx.controls;
    requires javafx.fxml;


    exports labtasks.tasks;
    opens labtasks.tasks to javafx.fxml;
}