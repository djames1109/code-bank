package org.castle.djames.scimforge.pojocreator.schemareader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import de.captaingoldfish.scim.sdk.common.constants.AttributeNames;
import de.captaingoldfish.scim.sdk.common.constants.ClassPathReferences;
import de.captaingoldfish.scim.sdk.common.schemas.Schema;
import de.captaingoldfish.scim.sdk.common.utils.JsonHelper;
import de.captaingoldfish.scim.sdk.server.schemas.validation.MetaSchemaValidator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;


/**
 * this class tries to identify the relations between the resource-types and its schemas if any relations are
 * present.
 */
@Slf4j
public class JsonRelationParser {

    private static final Schema RESOURCE_SCHEMA_SCHEMA = new Schema(JsonHelper.loadJsonDocument(ClassPathReferences.META_RESOURCE_SCHEMA_JSON));

    private static final Schema RESOURCE_TYPE_SCHEMA = new Schema(JsonHelper.loadJsonDocument(ClassPathReferences.META_RESOURCE_TYPES_JSON));

    private final List<FileInfoWrapper> fileInfoWrapperList;

    /**
     * a list of all json contents that represent SCIM "urn:ietf:params:scim:schemas:core:2.0:Schema"
     */
    @Getter
    private final List<FileInfoWrapper> resourceTypes;

    /**
     * a list of all json contents that represent SCIM "urn:ietf:params:scim:schemas:core:2.0:ResourceType"
     */
    @Getter
    private final List<FileInfoWrapper> resourceSchemas;

    public JsonRelationParser(List<FileInfoWrapper> fileInfoWrapperList) {
        this.fileInfoWrapperList = fileInfoWrapperList;
        this.resourceTypes = extractResourceTypes();
        this.resourceSchemas = extractResourceSchemas();
        logUnparseableFiles();
    }

    /**
     * will log the files that are being ignored because they are not valid SCIM schema representations
     */
    private void logUnparseableFiles() {
        for (FileInfoWrapper fileInfoWrapper : fileInfoWrapperList) {
            boolean isResourceSchema = resourceSchemas.stream().noneMatch(f -> {
                return f.getResourceFile().getAbsolutePath().equals(fileInfoWrapper.getResourceFile().getAbsolutePath());
            });
            boolean isResourceType = resourceTypes.stream().noneMatch(f -> {
                return f.getResourceFile().getAbsolutePath().equals(fileInfoWrapper.getResourceFile().getAbsolutePath());
            });

            if (!isResourceSchema && !isResourceType) {
                log.warn("File '{}' is not a valid SCIM file and is being ignored",
                        fileInfoWrapper.getResourceFile().getAbsolutePath());
            }
        }
    }

    /**
     * @return all resource types from the found json files
     */
    private List<FileInfoWrapper> extractResourceTypes() {
        List<FileInfoWrapper> tmpResourceSchemas = fileInfoWrapperList.stream().filter(fileInfoWrapper -> {
            JsonNode jsonNode = fileInfoWrapper.getJsonNode();
            SchemaType schemaType = SchemaType.getSchemaType(jsonNode);
            return SchemaType.RESOURCE_TYPE.equals(schemaType);
        }).collect(Collectors.toList());

        return validateResourceTypes(tmpResourceSchemas);
    }

    /**
     * @return all resources from the found json files
     */
    private List<FileInfoWrapper> extractResourceSchemas() {
        List<FileInfoWrapper> tmpResourceTypes = fileInfoWrapperList.stream().filter(fileInfoWrapper -> {
            JsonNode jsonNode = fileInfoWrapper.getJsonNode();
            SchemaType schemaType = SchemaType.getSchemaType(jsonNode);
            return SchemaType.RESOURCE_SCHEMA.equals(schemaType);
        }).collect(Collectors.toList());

        return validateResourceSchemas(tmpResourceTypes);
    }

    /**
     * tries to identify relations between resource-types and resource-schemas and wraps them together. If a
     * resource-schema has no relation to any resource-type it will be added anyway
     *
     * @return the list of all resource schema relations with their resource types and extensions.
     */
    public List<SchemaRelation> getSchemaRelations() {
        List<SchemaRelation> schemaRelationWrapperList = new ArrayList<>();

        for (FileInfoWrapper resourceTypeWrapper : resourceTypes) {
            String resourceSchemaUri = resourceTypeWrapper.getJsonNode().get(AttributeNames.RFC7643.SCHEMA).textValue();
            ArrayNode schemaExtensions = (ArrayNode) resourceTypeWrapper.getJsonNode()
                    .get(AttributeNames.RFC7643.SCHEMA_EXTENSIONS);
            List<String> extensionUris = new ArrayList<>();
            if (schemaExtensions != null && !schemaExtensions.isNull()) {
                for (JsonNode schemaExtension : schemaExtensions) {
                    if (schemaExtension instanceof ObjectNode) {
                        String extensionSchemaUri = schemaExtension.get(AttributeNames.RFC7643.SCHEMA).textValue();
                        extensionUris.add(extensionSchemaUri);
                    }
                }
            }
            FileInfoWrapper resourceSchemaWrapper = getResourceSchemaByUri(resourceSchemaUri).orElse(null);
            if (resourceSchemaWrapper == null) {
                final String resourceTypeName = resourceTypeWrapper.getJsonNode().get(AttributeNames.RFC7643.NAME).textValue();
                log.warn("ResourceType \"{}\" is ignored because the referenced schema \"{}\" could not be found",
                        resourceTypeName,
                        resourceSchemaUri);
                continue;
            }
            List<FileInfoWrapper> extensionNodes = extensionUris.stream().map(resourceSchemaUri1 -> {
                return getResourceSchemaByUri(resourceSchemaUri1).orElse(null);
            }).filter(Objects::nonNull).collect(Collectors.toList());
            SchemaRelation schemaRelationWrapper = new SchemaRelation(resourceTypeWrapper, resourceSchemaWrapper,
                    extensionNodes);
            schemaRelationWrapperList.add(schemaRelationWrapper);
        }

        addSchemaRelationsWithoutResourceType(schemaRelationWrapperList);

        return schemaRelationWrapperList;
    }

    /**
     * add schemas that have no resource-type so that the java pojos for those will also be created
     */
    private void addSchemaRelationsWithoutResourceType(List<SchemaRelation> schemaRelationWrapperList) {
        for (FileInfoWrapper resourceSchema : resourceSchemas) {
            String schemaId = resourceSchema.getJsonNode().get(AttributeNames.RFC7643.ID).textValue();
            boolean added = schemaRelationWrapperList.stream().anyMatch(relation -> {
                return new Schema(relation.getResourceSchema().getJsonNode()).getNonNullId().equals(schemaId);
            });
            if (!added) {
                schemaRelationWrapperList.add(new SchemaRelation(null, resourceSchema, Collections.emptyList()));
            }
        }
    }

    /**
     * tries to find a resource schema from the {@link #resourceSchemas} by its id attribute
     */
    private Optional<FileInfoWrapper> getResourceSchemaByUri(String resourceSchemaUri) {
        return resourceSchemas.stream()
                .filter(jsonNode -> jsonNode.getJsonNode()
                        .get(AttributeNames.RFC7643.ID)
                        .textValue()
                        .equals(resourceSchemaUri))
                .findAny();
    }

    /**
     * validates if the given resource schema is valid and does apply to its meta schema definition
     */
    private List<FileInfoWrapper> validateResourceSchemas(List<FileInfoWrapper> tmpResourceSchemas) {
        return tmpResourceSchemas.stream().filter(fileInfoWrapper -> {
            try {
                MetaSchemaValidator.getInstance().validateDocument(RESOURCE_SCHEMA_SCHEMA, fileInfoWrapper.getJsonNode());
                return true;
            } catch (Exception ex) {
                log.debug(ex.getMessage(), ex);
                log.info("Document '{}' is not a valid resource schema and will be ignored: {}",
                        fileInfoWrapper.getResourceFile().getAbsolutePath(),
                        ex.getMessage());
                return false;
            }
        }).collect(Collectors.toList());
    }

    /**
     * validates if the given resource-type is valid and does apply to its meta schema definition
     */
    private List<FileInfoWrapper> validateResourceTypes(List<FileInfoWrapper> tmpResourceTypes) {
        return tmpResourceTypes.stream().filter(fileInfoWrapper -> {
            try {
                MetaSchemaValidator.getInstance().validateDocument(RESOURCE_TYPE_SCHEMA, fileInfoWrapper.getJsonNode());
                return true;
            } catch (Exception ex) {
                log.debug(ex.getMessage(), ex);
                log.warn("Document '{}' is not a valid resource type and will be ignored: {}",
                        fileInfoWrapper.getResourceFile().getAbsolutePath(),
                        ex.getMessage());
                return false;
            }
        }).collect(Collectors.toList());
    }

    /**
     * supported types that are parseable into java pojos
     */
    public enum SchemaType {

        RESOURCE_TYPE, RESOURCE_SCHEMA, UNPARSEABLE;

        public static SchemaType getSchemaType(JsonNode jsonNode) {
            ArrayNode schemasNode = (ArrayNode) jsonNode.get(AttributeNames.RFC7643.SCHEMAS);
            if (schemasNode == null || schemasNode.isNull() || schemasNode.isEmpty()) {
                return UNPARSEABLE;
            }
            for (JsonNode node : schemasNode) {
                if (node instanceof TextNode) {
                    TextNode schema = (TextNode) node;
                    switch (schema.textValue()) {
                        case "urn:ietf:params:scim:schemas:core:2.0:Schema":
                            return RESOURCE_SCHEMA;
                        case "urn:ietf:params:scim:schemas:core:2.0:ResourceType":
                            return RESOURCE_TYPE;
                    }
                }
            }
            return UNPARSEABLE;
        }
    }
}
