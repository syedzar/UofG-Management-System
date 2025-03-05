module com.manage.uofgmanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.manage.uofgmanagement to javafx.fxml;
    exports com.manage.uofgmanagement;
}