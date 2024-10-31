package org.castle.djames.scimforge.pojocreator;


import com.fasterxml.jackson.databind.JsonNode;
import de.captaingoldfish.scim.sdk.common.schemas.Schema;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.castle.djames.scimforge.pojocreator.parser.EndpointDefinitionBuilder;
import org.castle.djames.scimforge.pojocreator.parser.ResourceHandlerBuilder;
import org.castle.djames.scimforge.pojocreator.parser.ResourceNodeBuilder;
import org.castle.djames.scimforge.pojocreator.parser.ScimConfigBuilder;
import org.castle.djames.scimforge.pojocreator.schemareader.*;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@ShellComponent
public class ShellController {

    /**
     * takes the given resources and writes the created pojos to the file system at the specified location
     */
    private static List<String> writeCreatedResourcesToFileSystem(String outputDir,
                                                                  String packageName,
                                                                  Map<Schema, String> resourceSchemaPojoMap,
                                                                  Map<JsonNode, String> endpointDefinitionPojoMap,
                                                                  Map<SchemaRelation, String> resourceHandlerPojoMap,
                                                                  String scimConfigPojo,
                                                                  boolean overrideExistingFiles) {
        List<String> resourceNodeFiles = PojoWriter.writeResourceNodesToFileSystem(resourceSchemaPojoMap,
                outputDir,
                packageName,
                overrideExistingFiles);
        List<String> endpointDefinitionFiles = PojoWriter.writeEndpointDefinitionsToFileSystem(endpointDefinitionPojoMap,
                outputDir,
                packageName,
                overrideExistingFiles);
        resourceNodeFiles.addAll(endpointDefinitionFiles);
        List<String> resourceHandlerFiles = PojoWriter.writeResourceHandlerToFileSystem(resourceHandlerPojoMap,
                outputDir,
                packageName,
                overrideExistingFiles);
        resourceNodeFiles.addAll(resourceHandlerFiles);

        Optional.ofNullable(scimConfigPojo)
                .flatMap(pojo -> PojoWriter.writeScimConfig(outputDir, packageName, pojo, overrideExistingFiles))
                .ifPresent(resourceNodeFiles::add);

        return resourceNodeFiles;
    }

    /**
     * will translate the schemas at the given location into java pojos representing the necessary structures for
     * the SCIM-SDK implementation
     */
    @SneakyThrows
    @ShellMethod(key = "translate", value = "Translate SCIM schemas to Java POJOs for SCIM SDK")
    public String translateSchemas(@ShellOption(value = {"-l", "--location"}, // @formatter:off
                                help = "a directory containing resource-schemas and resource-types or a "
                                  + "direct file location of a resource-schema") String schemaLocation, // @formatter:on
                                   @ShellOption(value = {"-r", "--recursive"}, //
                                           help = "if the given directory should be searched recursively", //
                                           defaultValue = "false") boolean recursive, //
                                   @ShellOption(value = {"-o", "--output"}, //
                                           help = "the output directory where the java POJOs will be placed. This directory "
                                                   + "should point to your 'src/main/java' directory", //
                                           defaultValue = ".") String outputDir,
                                   @ShellOption(value = {"-p", "--package"}, //
                                           help = "The name of the package for the generated POJOs", //
                                           defaultValue = "my.scim.sdk.app") String packageDir,
                                   @ShellOption(value = {"--useLombok"}, //
                                           help = "Add lombok @Builder annotations to constructors", //
                                           defaultValue = "false") boolean useLombok,
                                   @ShellOption(value = {"--override"}, //
                                           help = "Replace already existing files", //
                                           defaultValue = "false") boolean overrideExistingFiles,
                                   @ShellOption(value = {"--create-config"}, //
                                           help = "Creates a predefined SCIM configuration file with a ResourceEndpoint", //
                                           defaultValue = "false") boolean createScimConfig) {
        List<String> createdFiles = createPojos(schemaLocation,
                recursive,
                outputDir,
                packageDir,
                useLombok,
                overrideExistingFiles,
                createScimConfig);

        if (createdFiles.isEmpty()) {
            return "No files were created!";
        } else {
            return String.format("Successfully created the following files:\n- %s", String.join("\n- ", createdFiles));
        }
    }

    /**
     * creates the java pojos and stores them within the filesystem
     *
     * @return the list of absolute paths that were created
     */
    protected List<String> createPojos(String schemaLocation,
                                       boolean recursive,
                                       String outputDir,
                                       String packageName,
                                       boolean useLombok,
                                       boolean overrideExistingFiles,
                                       boolean createScimConfig) {
        File file = new File(schemaLocation);
        List<FileInfoWrapper> fileInfoWrapperList = FileSystemJsonReader.parseFileToJsonNode(file, recursive);
        JsonRelationParser relationParser = new JsonRelationParser(fileInfoWrapperList);
        List<SchemaRelation> schemaRelations = relationParser.getSchemaRelations();

        ResourceNodeBuilder resourceNodeBuilder = new ResourceNodeBuilder(useLombok);
        Map<Schema, String> resourceSchemaPojoMap = resourceNodeBuilder.createResourceSchemaPojos(packageName,
                schemaRelations);

        EndpointDefinitionBuilder endpointDefinitionBuilder = new EndpointDefinitionBuilder();
        Map<JsonNode, String> endpointDefinitionPojoMap = endpointDefinitionBuilder.createEndpointDefinitions(packageName,
                schemaRelations);

        ResourceHandlerBuilder resourceHandlerBuilder = new ResourceHandlerBuilder();
        Map<SchemaRelation, String> resourceHandlerPojoMap = resourceHandlerBuilder.createResourceHandlerPojos(packageName,
                schemaRelations);

        String scimConfigPojo = null;
        if (createScimConfig) {
            ScimConfigBuilder scimConfigBuilder = new ScimConfigBuilder();
            scimConfigPojo = scimConfigBuilder.createScimConfigPojo(packageName, schemaRelations, useLombok);
        }

        return writeCreatedResourcesToFileSystem(outputDir,
                packageName,
                resourceSchemaPojoMap,
                endpointDefinitionPojoMap,
                resourceHandlerPojoMap,
                scimConfigPojo,
                overrideExistingFiles);
    }


}
