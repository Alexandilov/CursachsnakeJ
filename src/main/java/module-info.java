module project.snakegame {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;


    opens project.snakegame to javafx.fxml;
    exports project.snakegame;
}