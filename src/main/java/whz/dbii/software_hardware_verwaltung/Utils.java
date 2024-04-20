package whz.dbii.software_hardware_verwaltung;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class Utils {
    public static String getLogin(){
        return initHandler().getLogin();
    }

    public static String getPassword(){
        return initHandler().getPassword();
    }

    public static CredentialHandler initHandler(){
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = null;
        CredentialHandler credentialHandler = null;
        try {
            saxParser = factory.newSAXParser();
            credentialHandler = new CredentialHandler();
            saxParser.parse("src/main/resources/whz/dbii/software_hardware_verwaltung/credentials.xml", credentialHandler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        return credentialHandler;
    }

    static class CredentialHandler extends DefaultHandler {
        private static final String LOGIN = "login";
        private static final String PASSWORD = "password";
        private String login;
        private String password;
        private StringBuilder elementValue;

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (elementValue == null) {
                elementValue = new StringBuilder();
            } else {
                elementValue.append(ch, start, length);
            }
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            switch (qName){
                case LOGIN, PASSWORD -> elementValue = new StringBuilder();
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            switch (qName) {
                case LOGIN:
                    login = elementValue.toString();
                    break;
                case PASSWORD:
                    password = elementValue.toString();
                    break;
            }
        }

        public String getLogin(){
            return login;
        }

        public String getPassword(){
            return password;
        }
    }
}
