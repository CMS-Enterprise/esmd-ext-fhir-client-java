package gov.cms.esmd.common.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Extension {
    private String url;
    private String valueString;
    private String valueCode;
    private String valueDateTime;

}
