package com.example.mecheduler.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HospitalInquire2 {
    @JacksonXmlProperty(localName = "body")
    private Body body;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @JacksonXmlRootElement(localName = "body")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Body {
        @JacksonXmlProperty(localName = "items")
        List<HospitalItem2> items;

        public List<HospitalItem2> getItems() {
            return items;
        }

        public void setItems(List<HospitalItem2> items) {
            this.items = items;
        }
    }
}
