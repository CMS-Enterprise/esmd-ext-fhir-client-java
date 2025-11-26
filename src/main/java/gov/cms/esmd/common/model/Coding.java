package gov.cms.esmd.common.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coding {
    private String system;
    private String code;
    private String display;

}
