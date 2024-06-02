package whz.dbii.software_hardware_verwaltung.model.xml;


import whz.dbii.software_hardware_verwaltung.model.hardware.Failure;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "failures")
@XmlRootElement
public class FailureList {

    @XmlElement
    public List<Failure> list = new ArrayList<>();
    public FailureList(List<Failure> list) {
        this.list = list;
    }

    public FailureList(){}
}
