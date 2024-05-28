package whz.dbii.software_hardware_verwaltung.model.xml;

import whz.dbii.software_hardware_verwaltung.model.Worker;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "workers")
@XmlRootElement
public class WorkerList {
    @XmlElement
    public List<Worker> list = new ArrayList<>();
    public WorkerList(List<Worker> list) {
        this.list = list;
    }

    public WorkerList(){}
}
