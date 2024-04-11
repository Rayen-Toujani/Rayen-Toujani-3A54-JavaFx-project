module Test{
    requires javafx.controls;
    requires javafx.fxml;
    requires  javafx.graphics;
    requires java.sql;
    opens com.visita.controllers;
    opens com.visita.models;
    opens com.visita.services;
    opens com.visita.test;
}