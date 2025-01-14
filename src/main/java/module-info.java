module com.hazuan.advancedprogrammingproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.derby.commons;
    requires org.apache.derby.tools;
    requires java.sql;

    opens com.hazuan.advancedprogrammingproject to javafx.fxml;
    exports com.hazuan.advancedprogrammingproject;
}