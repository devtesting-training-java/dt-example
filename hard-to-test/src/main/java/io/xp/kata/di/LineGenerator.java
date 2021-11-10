package io.xp.kata.di;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.xp.kata.outsideteamcontrol.ComplexFizzBuzzDto;
import io.xp.kata.outsideteamcontrol.FizzBuzz;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class LineGenerator {
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<String> generate() {
        Limits fromTo = Limits.getInstance();
        List<String> lines = new ArrayList<String>();
        int from = fromTo.getFrom();
        int to = fromTo.getTo();
        FizzBuzz fizzBuzz = new FizzBuzz();
        for (int i = from; i <= to; i++) {
            lines.add(getTransformed(fizzBuzz, i));
        }
        return lines;
    }

    private String getTransformed(FizzBuzz fizzBuzz, int i) {
        try {
            String json = fizzBuzz.transform(i);
            ComplexFizzBuzzDto dto = objectMapper.readerFor(ComplexFizzBuzzDto.class).readValue(json);
            if (checkDto(dto)) {
                return dto.getResult();
            }
        } catch (JsonProcessingException e) {
            return "";
        }
        return "";
    }

    private boolean checkDto(ComplexFizzBuzzDto dto) {
        if (dto.isSuccess() && notOlderThanYesterday(dto.getGeneratedTime())) {
            return hasValidToken(dto);
        }
        return false;
    }

    private boolean hasValidToken(ComplexFizzBuzzDto dto) {
        String token = dto.getAttributes().get("response").get("token");
        return token.matches("^\\d{9}[A-Z]{4}");
    }

    private boolean notOlderThanYesterday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        Calendar now = Calendar.getInstance();
        return calendar.after(yesterday) && calendar.before(now);
    }
}
