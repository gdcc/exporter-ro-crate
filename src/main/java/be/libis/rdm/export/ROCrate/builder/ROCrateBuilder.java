package be.libis.rdm.export.ROCrate.builder;
import java.util.LinkedHashMap;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;


public class ROCrateBuilder {
    final LinkedHashMap<String, ROCrateEntity> entities;

    public ROCrateBuilder() {
        this.entities = new LinkedHashMap<String, ROCrateEntity>();
    }



    public ROCrateEntity get(final String entityId) {
        final ROCrateEntity entity;
        if (this.entities.get(entityId)==null) {
            entity = new ROCrateEntity();
            this.entities.put(entityId, entity);       
        } else {
            entity = this.entities.get(entityId);
            entity.putProperty("@id", entityId);
        }
        return entity;
    }
    
    public void put(final String entityId, final ROCrateEntity entity) {
        this.entities.put(entityId, entity);
    }


    public void upsertEntity(final ROCrateEntity entity) {
        String id = entity.get("@id").values.get(0);
        this.get(id).updateProperties(entity.getProperties());
        
    }

    public JsonObject build( ) {
        final JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        final  JsonArrayBuilder graph = Json.createArrayBuilder();
        for (final ROCrateEntity entity:this.entities.values()) {
            JsonObject properties = entity.asJsonArray();
            graph.add(properties);
        }
        jsonObjectBuilder.add("@context", "https://w3id.org/ro/crate/1.1/context");
        jsonObjectBuilder.add("@graph", graph);
        return jsonObjectBuilder.build();
    }

}
