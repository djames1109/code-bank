package org.castle.djames.scimforge.command.config;

import de.captaingoldfish.scim.sdk.server.endpoints.authorize.Authorization;

import java.util.Collections;
import java.util.Map;
import java.util.Set;


public class ScimAuthentication implements Authorization {

    @Override
    public Set<String> getClientRoles() {
        return Collections.singleton("admin");
    }

    @Override
    public boolean authenticate(Map<String, String> httpHeaders, Map<String, String> queryParams) {
        return true;
    }
}
