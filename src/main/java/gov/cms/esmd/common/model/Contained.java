package gov.cms.esmd.common.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contained {
    private String resourceType;
    private String id;
    private List<Issue> issue;

}
