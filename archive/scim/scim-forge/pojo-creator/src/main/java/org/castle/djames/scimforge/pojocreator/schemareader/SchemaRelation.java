package org.castle.djames.scimforge.pojocreator.schemareader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
public class SchemaRelation {

    /**
     * the json representation of the resource type. Might be null
     */
    private final FileInfoWrapper resourceType;

    /**
     * the resource schema that is referenced within the {@link #resourceType}.
     */
    private final FileInfoWrapper resourceSchema;

    /**
     * the extensions of the {@link #resourceType}. If the {@link #resourceType} is null this collection will be
     * empty
     */
    private final List<FileInfoWrapper> extensions;
}
