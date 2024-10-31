// auto generated by: scim-sdk-schema-pojo-creator
package org.castle.djames.scimforge.api.endpoints;

import java.util.Arrays;

import de.captaingoldfish.scim.sdk.common.utils.JsonHelper;
import de.captaingoldfish.scim.sdk.server.endpoints.EndpointDefinition;
import de.captaingoldfish.scim.sdk.server.endpoints.ResourceHandler;

import org.castle.djames.scimforge.api.resources.User;

public class UserEndpointDefinition extends EndpointDefinition
{

  public UserEndpointDefinition(ResourceHandler<User> resourceHandler)
  {
    super(JsonHelper.loadJsonDocument("/scim/UserResourceType.json"),
          JsonHelper.loadJsonDocument("/scim/UserResource.json"),
          Arrays.asList(), resourceHandler);
  }
}