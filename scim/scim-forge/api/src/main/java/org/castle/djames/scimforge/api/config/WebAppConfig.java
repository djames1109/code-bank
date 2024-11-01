package org.castle.djames.scimforge.api.config;

import de.captaingoldfish.scim.sdk.common.resources.ServiceProvider;
import de.captaingoldfish.scim.sdk.common.resources.complex.*;
import de.captaingoldfish.scim.sdk.common.resources.multicomplex.AuthenticationScheme;
import de.captaingoldfish.scim.sdk.server.endpoints.ResourceEndpoint;
import de.captaingoldfish.scim.sdk.server.schemas.ResourceType;
import de.captaingoldfish.scim.sdk.server.schemas.custom.ResourceTypeFeatures;
import org.castle.djames.scimforge.api.endpoints.UserEndpointDefinition;
import org.castle.djames.scimforge.api.resourcehandler.UserResourceHandler;
import org.castle.djames.scimforge.api.service.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;


@Configuration
public class WebAppConfig {

    @Bean
    public ServiceProvider getServiceProviderConfig() {
        AuthenticationScheme authScheme = AuthenticationScheme.builder()
                .name("Bearer")
                .description("Authentication scheme using the OAuth "
                        + "Bearer Token Standard")
                .specUri("http://www.rfc-editor.org/info/rfc6750")
                .type("oauthbearertoken")
                .build();
        return ServiceProvider.builder()
                .filterConfig(FilterConfig.builder().supported(true).maxResults(50).build())
                .sortConfig(SortConfig.builder().supported(true).build())
                .changePasswordConfig(ChangePasswordConfig.builder().supported(true).build())
                .bulkConfig(BulkConfig.builder().supported(true).maxOperations(10).build())
                .patchConfig(PatchConfig.builder().supported(true).build())
                .authenticationSchemes(Collections.singletonList(authScheme))
                .eTagConfig(ETagConfig.builder().supported(true).build())
                .build();
    }

    /**
     * creates a resource endpoint for scim
     *
     * @param serviceProvider the service provider configuration
     * @return the resource endpoint
     */
    @Bean
    public ResourceEndpoint getResourceEndpoint(ServiceProvider serviceProvider) {
        return new ResourceEndpoint(serviceProvider);
    }

    /**
     * gets the user resource type that is the core object of the /Users endpoint. We will also activate the
     * auto-filtering extension so that filtering will work on the fly
     *
     * @param resourceEndpoint the resource endpoint that was previously defined
     * @return the user resource type
     */
    @Bean
    public ResourceType getUserResourceType(ResourceEndpoint resourceEndpoint) {
        ResourceType userResourceType = resourceEndpoint.registerEndpoint(new UserEndpointDefinition(new UserResourceHandler()));
        userResourceType.setFeatures(ResourceTypeFeatures.builder().autoFiltering(true).autoSorting(true).build());
        return userResourceType;
    }

//  /**
//   * gets the group resource type that is the core object of the /Groups endpoint. We will also activate the
//   * auto-filtering extension so that filtering will work on the fly
//   *
//   * @param resourceEndpoint the resource endpoint that was previously defined
//   * @return the group resource type
//   */
//  @Bean
//  public ResourceType getGroupResourceType(ResourceEndpoint resourceEndpoint)
//  {
//    ResourceType groupResourceType = resourceEndpoint.registerEndpoint(new GroupEndpointDefinition(new GroupHandler()));
//    groupResourceType.setFeatures(ResourceTypeFeatures.builder().autoFiltering(true).autoSorting(true).build());
//    return groupResourceType;
//  }

}
