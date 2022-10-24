package ru.uskov.testRelex.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
@Data
public class FileDTO {
    @JsonProperty("file_path")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String filePath;
    @JsonProperty("max_value")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Long maxValue;
    @JsonProperty("min_value")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Long minValue;
    @JsonProperty("median_value")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Double medianValue;
    @JsonProperty("avg_value")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Double avgValue;
    @JsonProperty("ascending_seq")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<List<Long>> ascendingSeq;
    @JsonProperty("descending_seq")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<List<Long>> descendingSeq;
    @JsonProperty("operation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String operation;


}
