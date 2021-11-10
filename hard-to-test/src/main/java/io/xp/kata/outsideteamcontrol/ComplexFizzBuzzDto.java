package io.xp.kata.outsideteamcontrol;

import java.util.Date;
import java.util.Map;

public class ComplexFizzBuzzDto {
    private String id;
    private String name;
    private Map<String, Map<String, String>> attributes;
    private String result;
    private Date generatedTime = new Date();
    private boolean success;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Map<String, String>> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Map<String, String>> attributes) {
        this.attributes = attributes;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getGeneratedTime() {
        return generatedTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
