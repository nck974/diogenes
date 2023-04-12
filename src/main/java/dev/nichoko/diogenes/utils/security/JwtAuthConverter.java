package dev.nichoko.diogenes.utils.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthConverter.class);

    private final JwtAuthConverterProperties properties;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        // This may be extended to use also JWT scope, but is not needed at the moment
        Collection<GrantedAuthority> authorities = extractResourceRoles(jwt).stream().collect(Collectors.toSet());
        if (logger.isDebugEnabled()) {
            logger.debug("Received a request with authentication: {}", authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(", ")));
        }
        return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));
    }

    /*
     * If SUB is provided use it otherwise use the default "preferred_name" from the
     * app properties
     */
    private String getPrincipalClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if (properties.getPrincipalAttribute() != null) {
            claimName = properties.getPrincipalAttribute();
        }
        return jwt.getClaim(claimName);
    }

    /*
     * Search inside the JWT the key resource_access. It contains something like:
     * resource_access: { "diogenes-client": { "roles": [ "diogenes-user"
     * A collection of SimpleGrantedAuthority will be returned with a role named
     * ROLE_<ROLE>
     */
    @SuppressWarnings("unchecked")
    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        Map<String, Object> resource;
        Collection<String> resourceRoles;

        if (resourceAccess == null
                || (resource = (Map<String, Object>) resourceAccess.get(properties.getResourceId())) == null
                || (resourceRoles = (Collection<String>) resource.get("roles")) == null) {
            return Set.of();
        }
        return resourceRoles.stream()
                .filter(role -> role.startsWith("diogenes-"))
                .map(role -> role.replace("diogenes-", ""))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toSet());
    }
}
