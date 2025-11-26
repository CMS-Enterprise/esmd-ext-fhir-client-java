package gov.cms.esmd.common.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {
    private String severity;
    private String code;
    private Details details;
    private String diagnostics;

}
