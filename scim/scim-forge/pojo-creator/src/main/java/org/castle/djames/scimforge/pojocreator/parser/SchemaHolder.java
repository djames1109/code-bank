package org.castle.djames.scimforge.pojocreator.parser;

import de.captaingoldfish.scim.sdk.common.schemas.Schema;
import lombok.Getter;
import org.castle.djames.scimforge.pojocreator.schemareader.FileInfoWrapper;
import org.castle.djames.scimforge.pojocreator.schemareader.SchemaRelation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * this class holds the schemas that should be created and differs between
 * {@link de.captaingoldfish.scim.sdk.common.resources.ResourceNode}s and extensions only that will be
 * extended by {@link de.captaingoldfish.scim.sdk.common.resources.base.ScimObjectNode}
 */
@Getter
public class SchemaHolder {

    /**
     * the schemas within the map will be extended by
     * {@link de.captaingoldfish.scim.sdk.common.resources.ResourceNode}
     */
    private final Map<String, Schema> resourceNodesToParse;

    /**
     * the schemas within the map will be extended by
     * {@link de.captaingoldfish.scim.sdk.common.resources.base.ScimObjectNode}
     */
    private final Map<String, Schema> extensionNodesToParse;

    private SchemaHolder(Map<String, Schema> resourceNodesToParse, Map<String, Schema> extensionNodesToParse) {
        this.resourceNodesToParse = resourceNodesToParse;
        this.extensionNodesToParse = extensionNodesToParse;
    }

    /**
     * analyzes the {@link SchemaRelation} objects and puts the schemas that are extensions only and that are
     * resource nodes into different maps
     *
     * @param schemaRelations the previously analyzed schema relations based on their resource types
     */
    public static SchemaHolder PartSchemas(List<SchemaRelation> schemaRelations) {
        Map<String, Schema> resourceNodesToParse = new HashMap<>();
        Map<String, Schema> extensionNodesToParse = new HashMap<>();
        for (SchemaRelation schemaRelation : schemaRelations) {
            Schema schema = new Schema(schemaRelation.getResourceSchema().getJsonNode());
            resourceNodesToParse.put(schema.getNonNullId(), schema);
            for (Schema extension : schemaRelation.getExtensions()
                    .stream()
                    .map(FileInfoWrapper::getJsonNode)
                    .map(Schema::new)
                    .collect(Collectors.toList())) {
                extensionNodesToParse.put(extension.getNonNullId(), extension);
            }
        }

        // leave only those schemas within the extensionNodesToParse Set that are extensions only and are not directly
        // referenced by any resource-type
        final List<String> entriesToRemove = new ArrayList<>();
        for (Map.Entry<String, Schema> idSchemaEntry : extensionNodesToParse.entrySet()) {
            Schema extensionSchema = idSchemaEntry.getValue();
            if (resourceNodesToParse.containsKey(extensionSchema.getNonNullId())) {
                entriesToRemove.add(extensionSchema.getNonNullId());
            }
        }
        entriesToRemove.forEach(extensionNodesToParse::remove);
        return new SchemaHolder(resourceNodesToParse, extensionNodesToParse);
    }
}
