package org.castle.djames.scimforge.pojocreator.parser;

import de.captaingoldfish.scim.sdk.common.constants.AttributeNames;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.castle.djames.scimforge.pojocreator.schemareader.SchemaRelation;
import org.castle.djames.scimforge.pojocreator.utils.UtilityMethods;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ResourceHandlerBuilder extends AbstractPojoBuilder {

    /**
     * the template to create {@link de.captaingoldfish.scim.sdk.server.endpoints.ResourceHandler}s
     */
    private final Template resourceHandlerTemplate;


    @SneakyThrows
    public ResourceHandlerBuilder() {
        this.resourceHandlerTemplate = FREEMARKER_CONFIGURATION.getTemplate("resource-handler.ftl");
    }

    /**
     * will create the resource handler based on the defined resource-types
     *
     * @param packageName     the base package name of the source file
     * @param schemaRelations all schema relations that were found. Processed only if a resource-type is present
     *                        for a resource schema
     * @return the created resource handler pojos
     */
    public Map<SchemaRelation, String> createResourceHandlerPojos(String packageName,
                                                                  List<SchemaRelation> schemaRelations) {
        Map<SchemaRelation, String> resourceHandlerPojos = new HashMap<>();
        for (SchemaRelation schemaRelation : schemaRelations) {
            if (schemaRelation.getResourceType() == null) {
                continue;
            }
            String createdPojo = createResourceHandlerJavaClass(packageName, schemaRelation);
            resourceHandlerPojos.put(schemaRelation, createdPojo);
        }
        return resourceHandlerPojos;
    }

    /**
     * creates the resource handler java pojo based on the given schemaRelation
     *
     * @param packageName    the base package name of the java pojo
     * @param schemaRelation the schemaRelation that contains all required information for creating a resource
     *                       handler
     * @return the string representation of the created resource handler java pojo
     */
    @SneakyThrows
    private String createResourceHandlerJavaClass(String packageName, SchemaRelation schemaRelation) {
        final String resourceTypeName = schemaRelation.getResourceType()
                .getJsonNode()
                .get(AttributeNames.RFC7643.NAME)
                .textValue();
        final String resourceNodeName = schemaRelation.getResourceSchema()
                .getJsonNode()
                .get(AttributeNames.RFC7643.NAME)
                .textValue();
        final String className = UtilityMethods.getResourceHandlerName(resourceTypeName);
        final String resourceName = UtilityMethods.getResourceName(resourceNodeName);
        final String resourceImport = String.format("%s.%s",
                UtilityMethods.getResourcesPackage(packageName, false),
                resourceName);

        Map<String, Object> input = new HashMap<>();
        input.put("packageName", UtilityMethods.getResourceHandlerPackage(packageName, false));
        input.put("resourceName", resourceName);
        input.put("className", className);
        input.put("resourceImport", resourceImport);

        final String processedTemplate;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             Writer fileWriter = new OutputStreamWriter(outputStream)) {
            resourceHandlerTemplate.process(input, fileWriter);
            processedTemplate = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
        }
        return processedTemplate;
    }
}
