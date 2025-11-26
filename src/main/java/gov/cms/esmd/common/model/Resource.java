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
public class Resource {

    private String resourceType;
    private String id;
    private Meta meta;
    private List<Contained> contained;
    private String status;
    private String mode;
    private String title;
    private String date;
    private List<Extension> extension;
    private List<Entry> entry;

}
