package org.castle.djames.scimforge.pojocreator.schemareader;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;


@Getter
@RequiredArgsConstructor
public class FileInfoWrapper {

    /**
     * the file that was read
     */
    private final File resourceFile;

    /**
     * the json representation of the read file
     */
    private final JsonNode jsonNode;
}
