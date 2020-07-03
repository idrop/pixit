package com.pixit.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "total",
        "totalHits",
        "hits"
})
public class PixabayResponse {

    @JsonProperty("total")
    private Integer total;
    @JsonProperty("totalHits")
    private Integer totalHits;
    @JsonProperty("hits")
    private List<PixabayHit> pixabayHits = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }

    @JsonProperty("totalHits")
    public Integer getTotalHits() {
        return totalHits;
    }

    @JsonProperty("totalHits")
    public void setTotalHits(Integer totalHits) {
        this.totalHits = totalHits;
    }

    @JsonProperty("hits")
    public List<PixabayHit> getPixabayHits() {
        return pixabayHits;
    }

    @JsonProperty("hits")
    public void setPixabayHits(List<PixabayHit> pixabayHits) {
        this.pixabayHits = pixabayHits;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}