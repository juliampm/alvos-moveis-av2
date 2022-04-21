module com.example.testeav1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.testeav1 to javafx.fxml;
    exports com.example.testeav1;
}