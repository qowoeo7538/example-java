package org.lucas.common.pojo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InvokeTarget implements Serializable {

    private String interfaceName;

    private String methodName;

    private String[] methodParamTypes;

    private Object[] methodParams;

    public InvokeTarget(String interfaceName, String methodName, String[] methodParamTypes, Object[] methodParams) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.methodParamTypes = methodParamTypes;
        this.methodParams = methodParams;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String[] getMethodParamTypes() {
        return methodParamTypes;
    }

    public void setMethodParamTypes(String[] methodParamTypes) {
        this.methodParamTypes = methodParamTypes;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public void setMethodParams(Object[] methodParams) {
        this.methodParams = methodParams;
    }

    public static InvokeTargetBuilder builder() {
        return new InvokeTargetBuilder();
    }

    public static class InvokeTargetBuilder {

        private String interfaceName;

        private String methodName;

        private List<String> methodParamTypes = new ArrayList<>();

        private List<Object> methodParams = new ArrayList<>();

        public InvokeTargetBuilder interfaceName(String interfaceName) {
            this.interfaceName = interfaceName;
            return this;
        }

        public InvokeTargetBuilder methodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public InvokeTargetBuilder methodParamType(String methodParamType) {
            this.methodParamTypes.add(methodParamType);
            return this;
        }

        public InvokeTargetBuilder methodParam(Object methodParam) {
            this.methodParams.add(methodParam);
            return this;
        }

        public InvokeTargetBuilder methodParamToJson(String json, Class<?> clazz) {
            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
            this.methodParams.add(gson.fromJson(json, clazz));
            return this;
        }

        public InvokeTarget build() {
            return new InvokeTarget(this.interfaceName, this.methodName, this.methodParamTypes.toArray(new String[0]), this.methodParams.toArray(new Object[0]));
        }

    }
}
