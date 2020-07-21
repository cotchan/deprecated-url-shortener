package com.project.comento.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(builderClassName = "RequestUrlBuilder")
@JsonDeserialize(builder = RequestUrl.RequestUrlBuilder.class)
public class RequestUrl {
    final private String url;

    @JsonPOJOBuilder(withPrefix = "")
    public static class RequestUrlBuilder {
    }
}
