package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.event.Event;

import java.io.IOException;

/**
 * DTO for {@link Event}
 * Used for events in a location region
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventLocDto {
    private Long id;
    private String title;
    private String annotation;
    @JsonSerialize(using = DecimalJsonSerializer.class)
    private Double distance;

    static class DecimalJsonSerializer extends JsonSerializer<Double> {
        @Override
        public void serialize(Double value, JsonGenerator jgen, SerializerProvider provider)
                throws IOException {
            jgen.writeString(String.format("%.3f", value));
        }
    }
}

