package org.lucas.example.persistence.mysql.common.genbean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeanGenerator {

    /**
     * 支持的数据类型
     */
    private static final Set<String> TYPES = new HashSet<>(Arrays.asList("int", "double", "float", "boolean", "String",
            "Ineteger", "Double", "Float", "Boolean"));

    /**
     * getter 方法标识
     */
    private static final int GETTER = 0;

    /**
     * setter 方法标识
     */
    private static final int SETTER = 1;

    private StringBuilder beanCodeBuffer = new StringBuilder();

    private StringBuilder commentBuffer = new StringBuilder();

    /**
     * 生成 bean 字段属性
     *
     * @param pack 属性信息
     */
    private void generatePackage(String pack) {
        beanCodeBuffer.append("package " + pack + ";\r\n");
    }

    private void generateComment(String comment) {
        beanCodeBuffer.append("/**\r\n");
        beanCodeBuffer.append(wrapComment(" * " + comment + "\r\n", 80, false));
        beanCodeBuffer.append("\r\n */\r\n");
    }

    /**
     * 生成 bean 字段属性
     *
     * @param properties 属性信息
     */
    private void generateFields(List<Property> properties) {
        for (Property property : properties) {
            beanCodeBuffer.append("\tprivate " + property.getType() + " " + decapitalize(property.getName()) + ";\r\n");
        }
        beanCodeBuffer.append("\r\n");
    }

    /**
     * 生成 getter 方法
     *
     * @param properties 属性信息
     */
    private void generateGetters(List<Property> properties) {
        for (Property property : properties) {
            generatePropertyComment(property, GETTER);
            beanCodeBuffer.append("\tpublic " + property.getType() + " get" + capitalize(property.getName())
                    + "() {\r\n");
            beanCodeBuffer.append("\t\treturn " + decapitalize(property.getName()) + ";\r\n\t}");
            beanCodeBuffer.append("\r\n");
        }
    }

    /**
     * 生成 setter 方法
     *
     * @param properties bean 属性信息
     */
    private void generateSetters(List<Property> properties) {
        for (Property property : properties) {
            generatePropertyComment(property, SETTER);
            beanCodeBuffer.append("\tpublic void set" + capitalize(property.getName()) + "("
                    + property.getType() + "  " + decapitalize(property.getName()) + ") {\r\n");
            beanCodeBuffer.append("\t\tthis." + decapitalize(property.getName()) + "=" + decapitalize(property.getName())
                    + ";\r\n\t}");
            beanCodeBuffer.append("\r\n");
        }
    }

    /**
     * 生成注释
     *
     * @param property 属性
     * @param type     方法类型，{@link #GETTER},{@link #SETTER}.
     */
    private void generatePropertyComment(Property property, int type) {
        if (property.getComment().isEmpty()) {
            return;
        }
        beanCodeBuffer.append("\t/**\r\n\t");
        if (type == GETTER) {
            beanCodeBuffer.append(wrapComment(" * Returns " + property.getComment() + "\r\n", 80, true));
            beanCodeBuffer.append(wrapComment("\t * @return " + property.getComment(), 80, true));
        } else if (type == SETTER) {
            beanCodeBuffer.append(wrapComment(" * Sets " + property.getComment() + "\r\n", 80, true));
            beanCodeBuffer
                    .append(wrapComment("\t * @param " + property.getName() + " is " + property.getComment(), 80, true));
        }

        beanCodeBuffer.append("\r\n\t */\r\n");
    }

    /**
     * 注释信息处理
     *
     * @param comment  注释信息
     * @param numChars 每行多少字符
     */
    private String wrapComment(String comment, int numChars, boolean isTable) {
        commentBuffer.setLength(0);
        commentBuffer.append(comment);
        int i = 0;
        while (i + numChars < commentBuffer.length() && (i = commentBuffer.lastIndexOf(" ", i + numChars)) != -1) {
            commentBuffer.replace(i, i + 1, "\n\t * \t");
        }
        return commentBuffer.toString();
    }

    /**
     * 字段名称转换
     *
     * @param field 字段名称
     */
    private static String decapitalize(String field) {
        return Character.toLowerCase(field.charAt(0)) + field.substring(1);
    }

    private static String capitalize(String field) {
        return Character.toUpperCase(field.charAt(0)) + field.substring(1);
    }

    private boolean checkTypes(List<Property> properties) {
        boolean isValid = true;

        for (Property prop : properties) {
            if (!TYPES.contains(prop.getType())) {
                System.out.println("Type of property " + prop.getName() + " is not supported (" + prop.getType() + ")");
                isValid = false;
            }
        }

        return isValid;
    }

    private String generateBean(BeanBuilder info) {
        beanCodeBuffer.setLength(0);
        commentBuffer.setLength(0);

        String pack = info.getPack();
        String comment = info.getComment();
        List<Property> properties = info.getProperties();

        if (!checkTypes(properties)) {
            return "Pogrešni tip!";
        }
        generatePackage(pack);
        generateComment(comment);
        beanCodeBuffer.append("public class " + info.getName() + " {\r\n");
        generateFields(properties);
        generateGetters(properties);
        generateSetters(properties);
        beanCodeBuffer.append("}");
        return beanCodeBuffer.toString();
    }

    public static BeanBuilder builder() {
        return new BeanBuilder();
    }

    public static class BeanBuilder {

        private String pack;

        private String name = "";

        private String comment = "";

        private List<String> imports;

        private List<Property> props;

        public String build() {
            return new BeanGenerator().generateBean(this);
        }

        public BeanBuilder pack(String pack) {
            this.pack = pack;
            return this;
        }

        public BeanBuilder name(String name) {
            this.name = name;
            return this;
        }

        public BeanBuilder imports(List<String> imports) {
            this.imports = imports;
            return this;
        }

        public BeanBuilder properties(List<Property> props) {
            this.props = props;
            return this;
        }

        public BeanBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        private String getPack() {
            return pack;
        }

        private String getName() {
            return name;
        }

        private String getComment() {
            return comment;
        }

        private List<String> getImports() {
            return imports;
        }

        private List<Property> getProperties() {
            return props;
        }
    }

}
