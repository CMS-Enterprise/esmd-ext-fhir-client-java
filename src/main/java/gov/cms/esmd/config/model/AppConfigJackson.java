package gov.cms.esmd.config.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppConfigJackson {
    private AppSettings appSettings;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AppSettings {
        private double httpClientRequestTimeOutSeconds;
        private String baseFileLocationFolder;
        private String fhirServerUrl;
        private String endPointBaseUrl;

        private AuthenticationAPI authenticationAPI;
        private PresignedURLAPI presignedURLAPI;
        private UploadClinicalDocumentAPI uploadClinicalDocumentAPI;
        private BundleSubmissionAPI bundleSubmissionAPI;
        private NotificationRetrievalAPI notificationRetrievalAPI;
        private DocumentRetrievalAPI documentRetrievalAPI;
        private DeliveryConfirmationAPI deliveryConfirmationAPI;
        private PractitionerAPI practitionerAPI;
        private BinaryAPI binaryAPI;
        private BundlePractitionerAPI bundlePractitionerAPI;

        // --- Authentication ---
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class AuthenticationAPI {
            private String clientId;
            private String clientSecret;
            private String scope;
            private String endpointURL;
            private String contentType;
            private double httpClientRequestTimeOutSeconds;
        }


        // --- Presigned URL API ---
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class PresignedURLAPI {
            private String endpointURL;
            private String contentType;
            private String accept;
            private double httpClientRequestTimeOutSeconds;
            private Request request;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Request {
                private String resourceType;
                private String id;
                private List<Parameter> parameter;

                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                @JsonInclude(JsonInclude.Include.NON_NULL)
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Parameter {
                    private String name;
                    private String valueString;
                    private List<Part> part;

                    @Data
                    @NoArgsConstructor
                    @AllArgsConstructor
                    @JsonInclude(JsonInclude.Include.NON_NULL)
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class Part {
                        private String name;
                        private String valueString;
                    }
                }
            }
        }

        // --- Upload Clinical Document ---
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class UploadClinicalDocumentAPI {
            private String contentType;
            private double httpClientRequestTimeOutSeconds;
            private String fileName;
            private String contentMD5;
        }

        // --- Bundle Submission API ---
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class BundleSubmissionAPI {
            private String endpointURL;
            private String contentType;
            private String accept;
            private double httpClientRequestTimeOutSeconds;
            private Request request;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Request {
                private String resourceType;
                private String id;
                private Meta meta;
                private String type;
                private String timestamp;
                private List<Entry> entry;

                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                @JsonInclude(JsonInclude.Include.NON_NULL)
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Meta {
                    private List<String> profile;
                    private List<Security> security;

                    @Data
                    @NoArgsConstructor
                    @AllArgsConstructor
                    @JsonInclude(JsonInclude.Include.NON_NULL)
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class Security {
                        private String system;
                        private String code;
                        private String display;
                    }
                }

                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                @JsonInclude(JsonInclude.Include.NON_NULL)
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Entry {
                    private Item item;

                    @Data
                    @NoArgsConstructor
                    @AllArgsConstructor
                    @JsonInclude(JsonInclude.Include.NON_NULL)
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class Item {
                        private Reference reference;

                        @Data
                        @NoArgsConstructor
                        @AllArgsConstructor
                        @JsonInclude(JsonInclude.Include.NON_NULL)
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public static class Reference {
                            private String reference;
                        }
                    }
                }
            }
        }

        // --- Notification Retrieval API ---
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class NotificationRetrievalAPI {
            private String endpointURL;
            private String accept;
            private double httpClientRequestTimeOutSeconds;
            private List<RequestParameter> requestParameters;
        }

        // --- Document Retrieval API ---
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DocumentRetrievalAPI {
            private String endpointURL;
            private String accept;
            private double httpClientRequestTimeOutSeconds;
            private List<RequestParameter> requestParameters;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class RequestParameter {
            private String name;
            private String value;
            private boolean inject;
        }

        // --- Delivery Confirmation API ---
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DeliveryConfirmationAPI {
            private String endpointURL;
            private String accept;
            private String contentType;
            private double httpClientRequestTimeOutSeconds;
            private Request request;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Request {
                private String resourceType;
                private String id;
                private Meta meta;
                private List<Contained> contained;
                private List<Extension> extension;
                private String status;
                private String mode;
                private String title;
                private String date;
                private List<Entry> entry;

                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                @JsonInclude(JsonInclude.Include.NON_NULL)
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Meta {
                    private List<String> profile;
                }

                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                @JsonInclude(JsonInclude.Include.NON_NULL)
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Contained {
                    private String resourceType;
                    private String id;
                    private Meta meta;
                    private List<Issue> issue;

                    @Data
                    @NoArgsConstructor
                    @AllArgsConstructor
                    @JsonInclude(JsonInclude.Include.NON_NULL)
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class Meta {
                        private List<String> profile;
                        private List<Security> security;

                        @Data
                        @NoArgsConstructor
                        @AllArgsConstructor
                        @JsonInclude(JsonInclude.Include.NON_NULL)
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public static class Security {
                            private String system;
                            private String code;
                            private String display;
                        }
                    }

                    @Data
                    @NoArgsConstructor
                    @AllArgsConstructor
                    @JsonInclude(JsonInclude.Include.NON_NULL)
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class Issue {
                        private String severity;
                        private String code;
                        private String diagnostics;
                    }
                }

                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                @JsonInclude(JsonInclude.Include.NON_NULL)
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Extension {
                    private String url;
                    private String valueString;
                    private String valueCode;
                    private String valueDateTime;
                    private String valueDate;
                }

                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                @JsonInclude(JsonInclude.Include.NON_NULL)
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Entry {
                    private Item item;

                    @Data
                    @NoArgsConstructor
                    @AllArgsConstructor
                    @JsonInclude(JsonInclude.Include.NON_NULL)
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class Item {
                        private Reference reference;

                        @Data
                        @NoArgsConstructor
                        @AllArgsConstructor
                        @JsonInclude(JsonInclude.Include.NON_NULL)
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public static class Reference {
                            private String reference;
                        }
                    }
                }
            }
        }

        // --- Practitioner API ---
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class PractitionerAPI {
            private String endpointURL;
            private String accept;
            private String contentType;
            private double httpClientRequestTimeOutSeconds;
            private Request request;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Request {
                private String resourceType;
                private String id;
                private Meta meta;
                private List<Name> name;
                private List<Telecom> telecom;
                private List<Address> address;
                private String gender;
                private boolean active;
                private List<Extension> extension;

                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                @JsonInclude(JsonInclude.Include.NON_NULL)
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Meta {
                    private List<String> profile;
                    private List<Security> security;

                    @Data
                    @NoArgsConstructor
                    @AllArgsConstructor
                    @JsonInclude(JsonInclude.Include.NON_NULL)
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class Security {
                        private String system;
                        private String code;
                        private String display;
                    }
                }

                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                @JsonInclude(JsonInclude.Include.NON_NULL)
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Name {
                    private String family;
                    private List<String> given;
                    private List<String> prefix;
                    private List<String> suffix;
                }

                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                @JsonInclude(JsonInclude.Include.NON_NULL)
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Telecom {
                    private String system;
                    private String value;
                    private String use;
                }

                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                @JsonInclude(JsonInclude.Include.NON_NULL)
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Address {
                    private String use;
                    private List<String> line;
                    private String city;
                    private String state;
                    private String postalCode;
                }

                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                @JsonInclude(JsonInclude.Include.NON_NULL)
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Extension {
                    private String url;
                    private String valueString;
                    private String valueCode;
                    private String valueDateTime;
                    private String valueDate;
                }
            }
        }

        // --- Binary API ---
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class BinaryAPI {
            private String endpointURL;
            private String accept;
            private String fileNameId;
            private double httpClientRequestTimeOutSeconds;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class BundlePractitionerAPI {
            private String endpointURL;
            private String contentType;
            private String accept;
            private double httpClientRequestTimeOutSeconds;
            private Request request;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Request {
                private String resourceType;
                private String id;
                private Meta meta;
                private String type;
                private String timestamp;
                private List<Entry> entry;

                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                @JsonInclude(JsonInclude.Include.NON_NULL)
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Meta {
                    private List<String> profile;
                    private List<Security> security;

                    @Data
                    @NoArgsConstructor
                    @AllArgsConstructor
                    @JsonInclude(JsonInclude.Include.NON_NULL)
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class Security {
                        private String system;
                        private String code;
                        private String display;
                    }
                }

                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                @JsonInclude(JsonInclude.Include.NON_NULL)
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Entry {
                    private String fullUrl;
                    private Resource resource;
                    private RequestAction request;

                    @Data
                    @NoArgsConstructor
                    @AllArgsConstructor
                    @JsonInclude(JsonInclude.Include.NON_NULL)
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class Resource {
                        private String resourceType;
                        private String id;
                        private Meta meta;
                        private List<Telecom> telecom;
                        private List<Name> name;
                        private List<Address> address;
                        private String gender;
                        private Boolean active;
                        private List<Extension> extension;

                        @Data
                        @NoArgsConstructor
                        @AllArgsConstructor
                        @JsonInclude(JsonInclude.Include.NON_NULL)
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public static class Meta {
                            private List<String> profile;
                            private List<Security> security;

                            @Data
                            @NoArgsConstructor
                            @AllArgsConstructor
                            @JsonInclude(JsonInclude.Include.NON_NULL)
                            @JsonIgnoreProperties(ignoreUnknown = true)
                            public static class Security {
                                private String system;
                                private String code;
                                private String display;
                            }
                        }

                        @Data
                        @NoArgsConstructor
                        @AllArgsConstructor
                        @JsonInclude(JsonInclude.Include.NON_NULL)
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public static class Telecom {
                            private String system;
                            private String value;
                            private String use;
                        }

                        @Data
                        @NoArgsConstructor
                        @AllArgsConstructor
                        @JsonInclude(JsonInclude.Include.NON_NULL)
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public static class Name {
                            private String family;
                            private List<String> given;
                            private List<String> prefix;
                        }

                        @Data
                        @NoArgsConstructor
                        @AllArgsConstructor
                        @JsonInclude(JsonInclude.Include.NON_NULL)
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public static class Address {
                            private String use;
                            private List<String> line;
                            private String city;
                            private String state;
                            private String postalCode;
                        }

                        @Data
                        @NoArgsConstructor
                        @AllArgsConstructor
                        @JsonInclude(JsonInclude.Include.NON_NULL)
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public static class Extension {
                            private String url;
                            private String valueString;
                            private String valueCode;
                            private String valueDate;
                        }
                    }

                    @Data
                    @NoArgsConstructor
                    @AllArgsConstructor
                    @JsonInclude(JsonInclude.Include.NON_NULL)
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class RequestAction {
                        private String method;
                        private String url;
                    }
                }
            }
        }

    }
}
