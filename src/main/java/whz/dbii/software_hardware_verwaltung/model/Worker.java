package whz.dbii.software_hardware_verwaltung.model;

import javafx.beans.property.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "worker")
@XmlRootElement
public class Worker {

    private IntegerProperty id;
    private StringProperty name;
    private StringProperty surname;
    private StringProperty email;

    public Worker(){
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.surname = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
    }

    public Worker(String firstName, String lastName, String email) {
        this.name = new SimpleStringProperty(firstName);
        this.surname = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
}
