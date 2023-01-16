package br.com.erudio.configuration;

import java.util.List;

import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
	
	public List<GroupedOpenApi> apis(SwaggerUiConfigParameters config,
			RouteDefinitionLocator locator) {
		
		var definitions = locator.getRouteDefinitions().collectList().block();
		
		definitions.stream().filter(
					routeDefinition -> routeDefinition.getId()
						.matches(".*-service"))
							.forEach(routeDefinition -> {
								String name = routeDefinition.getId();
								config.addGroup(name);
								GroupedOpenApi.builder()
									.pathsToMatch("/" + name + "/**")
									.group(name).build();
							}
				);
		return new ArrayList<>();
	}
}
