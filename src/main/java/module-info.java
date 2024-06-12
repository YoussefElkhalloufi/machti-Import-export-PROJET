module com.example.machti_import_export {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.example.machti_import_export to javafx.fxml;
    exports com.example.machti_import_export;
    exports com.example.machti_import_export.Controller;
    opens com.example.machti_import_export.Controller to javafx.fxml;
    exports com.example.machti_import_export.MachtiSte;
    opens com.example.machti_import_export.MachtiSte to javafx.fxml;
}