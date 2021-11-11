package io.xp.kata.outsideteamcontrol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.HashMap;
import java.util.Map;

public class FizzBuzz {

    private ObjectMapper objectMapper = new ObjectMapper();

    public final String transform(int i) {
        HardToTestUtil.throwExceptionInTest();
        String result = doTransform(i);
        ComplexFizzBuzzDto dto = new ComplexFizzBuzzDto();
        HashMap<String, Map<String, String>> attributes = new HashMap<>();
        Map<String, String> reqMap = new HashMap<>();
        Map<String, String> rspMap = new HashMap<>();
        attributes.put("request", reqMap);
        attributes.put("response", rspMap);
        reqMap.put("value", String.valueOf(i));
        rspMap.put("token", "134567899ADFE");
        rspMap.put("original_value", String.valueOf(i));
        dto.setSuccess(true);
        dto.setAttributes(attributes);
        dto.setResult(result);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
        }
        return toJson(dto);
    }

    private String toJson(ComplexFizzBuzzDto dto) {
        ObjectWriter writer = objectMapper.writer().withDefaultPrettyPrinter();
        try {
            return writer.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            return "{\"error\": \"generate json error\"}";
        }

    }

    private String doTransform(int i) {
        if (i % 15 == 0) {
            return "FizzBuzz";
        }
        if (i % 3 == 0) {
            return "Fizz";
        }
        if (i % 5 == 0) {
            return "Buzz";
        }
        return String.valueOf(i);
    }
}
