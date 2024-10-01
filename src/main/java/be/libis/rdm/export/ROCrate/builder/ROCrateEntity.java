package be.libis.rdm.export.ROCrate.builder;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Set;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class ROCrateEntity {

    private LinkedHashMap<String, ROCrateEntityProperty> properties;

    public ROCrateEntity() {
        this.properties = new LinkedHashMap<String, ROCrateEntityProperty>();
    }

    public LinkedHashMap<String, ROCrateEntityProperty> getProperties() {
        return properties;
    }

    private JsonObject asJsonObject(String key, String value) {
        final JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add(key, value);
        return jsonObjectBuilder.build();
    }

    public JsonObject asJsonArray() {
        final JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (final String propertyName : properties.keySet()) {

            final ROCrateEntityProperty property = properties.get(propertyName);
            if (property.asObjects().size() > 1) {
                final JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                for (Object propertyValue : property.asObjects()) {
                    if (propertyValue instanceof JsonArray) {
                        jsonArrayBuilder.add((JsonArray) propertyValue);
                    } else if (propertyValue instanceof JsonObject) {
                        jsonArrayBuilder.add((JsonObject) propertyValue);
                    } else if (propertyValue instanceof String) {
                        jsonArrayBuilder.add((String) propertyValue);
                    }
                }
                jsonObjectBuilder.add(propertyName, jsonArrayBuilder);
            } else if (property.asObjects().size()==1) {
                Object propertyValue = property.asObjects().get(0);
                if (propertyValue instanceof JsonArray) {
                    jsonObjectBuilder.add(propertyName, (JsonArray) propertyValue);
                } else if (propertyValue instanceof JsonObject) {
                    jsonObjectBuilder.add(propertyName, (JsonObject) propertyValue);
                } else if (propertyValue instanceof String) {
                    jsonObjectBuilder.add(propertyName, (String) propertyValue);
                }

            }

        }
        return jsonObjectBuilder.build();
    }

    public ROCrateEntityProperty get(String propertyName) {
        final ROCrateEntityProperty property;
        if (properties.get(propertyName) == null) {
            property = new ROCrateEntityProperty();
            properties.put(propertyName, property);
        } else {
            property = properties.get(propertyName);
        }
        return property;
    }

    public void updateProperties(final LinkedHashMap<String, ROCrateEntityProperty> propertiesToMerge) {
        for (final String propertyName : propertiesToMerge.keySet()) {
            if (properties.get(propertyName) == null) {
                properties.put(propertyName, propertiesToMerge.get(propertyName));
            } else if (!propertyName.equals("@id")) {
                properties.get(propertyName).merge(propertiesToMerge.get(propertyName).values);
            }

        }
    }

    public void putProperty(String propertyName, ArrayList<String> propertyValues) {
        final ROCrateEntityProperty property = get(propertyName);
        property.merge(propertyValues);
        properties.put(propertyName, property);
    }

    public void putProperty(String propertyName, String propertyValue) {
        final ROCrateEntityProperty property = get(propertyName);
        property.add(propertyValue);
        properties.put(propertyName, property);
    }

    public void putProperty(String propertyName, String propertyValue, String refersTo) {
        final ROCrateEntityProperty property = get(propertyName);
        property.add(propertyValue);
        property.refersTo = refersTo;
        properties.put(propertyName, property);
    }
    public void putProperty(String propertyName, ArrayList<String> propertyValues, String refersTo) {
        final ROCrateEntityProperty property = get(propertyName);
        property.merge(propertyValues);
        property.refersTo = refersTo;
        properties.put(propertyName, property);
    }

    @Override
    public String toString() {
        return this.properties.toString();
    }

}
