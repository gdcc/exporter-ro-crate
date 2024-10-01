package be.libis.rdm.export.ROCrate.builder;

import java.util.ArrayList;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;

public class ROCrateEntityProperty {
    public final ArrayList<String> values;
    public String refersTo;
    public boolean isEmpty;

    public boolean getIsEmpty() {
        if (values.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<String> getValues() {
        return this.values;
    }

    public ROCrateEntityProperty() {
        this.values = new ArrayList<String>();
    }

    public ROCrateEntityProperty(String value) {
        this.values = new ArrayList<String>();
        this.add(value);
        }

    public void clearValues() {
        this.values.clear();
    }

    public void add(final String valueToAdd) {
        if (!valueToAdd.isEmpty() && !this.values.contains(valueToAdd)) {
            this.values.add(valueToAdd);
        }
    }
    public void merge(ArrayList<String> values) {
        for (final String valueToAdd: values) {
            this.add(valueToAdd);
        }

    }
    
    public Object getValue(int i) {
        String value = values.get(i);
        if (this.refersTo!=null && !this.refersTo.isEmpty()) {
            final JsonObjectBuilder valueJsonObjectBuilder = Json.createObjectBuilder();
            valueJsonObjectBuilder.add("@id", value);
            return valueJsonObjectBuilder.build();
        } else {
            return value;
        }

    }

    

    public ArrayList<Object> asObjects() {
        // returns a list of Object (string or JsonObject)
        ArrayList<Object> result = new ArrayList<Object>();
        for (int i=0; i < values.size(); i++)
         {
            result.add(getValue(i));
        } 
        return result;
    }

    @Override
    public String toString() {
        return this.values.toString();
    }

}
