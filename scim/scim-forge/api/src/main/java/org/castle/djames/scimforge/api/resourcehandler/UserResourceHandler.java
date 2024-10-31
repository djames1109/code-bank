// auto generated by: scim-sdk-schema-pojo-creator
package org.castle.djames.scimforge.api.resourcehandler;

import java.util.List;
import java.util.ArrayList;

import de.captaingoldfish.scim.sdk.common.constants.enums.SortOrder;
import de.captaingoldfish.scim.sdk.common.schemas.SchemaAttribute;
import de.captaingoldfish.scim.sdk.server.endpoints.Context;
import de.captaingoldfish.scim.sdk.server.endpoints.ResourceHandler;
import de.captaingoldfish.scim.sdk.server.filter.FilterNode;
import de.captaingoldfish.scim.sdk.server.response.PartialListResponse;

import org.castle.djames.scimforge.api.resources.User;


/**
 *
 */
public class UserResourceHandler extends ResourceHandler<User>
{

  /**
   * {@inheritDoc}
   */
  @Override
  public User createResource(User resource, Context context)
  {
    // TODO
    return resource;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public User getResource(String id,
                          List<SchemaAttribute> attributes,
                          List<SchemaAttribute> excludedAttributes,
                          Context context)
  {
    // TODO
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PartialListResponse<User> listResources(long startIndex,
                                                 int count,
                                                 FilterNode filter,
                                                 SchemaAttribute sortBy,
                                                 SortOrder sortOrder,
                                                 List<SchemaAttribute> attributes,
                                                 List<SchemaAttribute> excludedAttributes,
                                                 Context context)
  {
    // TODO
    return PartialListResponse.<User> builder().resources(new ArrayList<>()).totalResults(0).build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public User updateResource(User resource, Context context)
  {
    return resource;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteResource(String id, Context context)
  {
    // TODO
  }
}
