package org.lucas.example.persistence.mysql.common.genbean;

import java.util.List;

public class BeanGenerator {

    private StringBuilder beanCodeBuffer = new StringBuilder();
    private StringBuilder commentBuffer = new StringBuilder();

    private void generateFields(List<Property> properties) {
        for (Property property : properties) {
            beanCodeBuffer.append("\tprivate " + property.getType() + " " + decapitalize(property.getName()) + ";\r\n");
        }
        beanCodeBuffer.append("\r\n");
    }

    private static String decapitalize(String field) {
        return Character.toLowerCase(field.charAt(0)) + field.substring(1);
    }

}
