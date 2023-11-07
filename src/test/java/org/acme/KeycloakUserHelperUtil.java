package org.acme;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

class KeycloakUserHelperUtil {
    private final RealmResource realmResource;

    public KeycloakUserHelperUtil(RealmResource realmResource) {
        this.realmResource = realmResource;
    }

    public void addRole(String roleToCreate) {
        RoleRepresentation roleRepresentation = new RoleRepresentation(roleToCreate, roleToCreate, false);
        try {
            realmResource.roles().get(roleToCreate).toRepresentation();
        } catch (WebApplicationException ex) {
            realmResource.roles().create(roleRepresentation);
        }
    }

    void addOrReplaceUser(String username, Set<String> roleNames) {
        UsersResource usersResource = realmResource.users();
        // deleteIfExisting(username, usersResource);
        String userId = createUser(username, usersResource);
        UserResource userResource = usersResource.get(userId);
        findAndAddRoles(roleNames, userResource, realmResource);
    }

    private void findAndAddRoles(Set<String> roleNames, UserResource userResource, RealmResource realmResource) {
        List<RoleRepresentation> rolesRepresentation = roleNames.stream()
                .map(roleName -> findRole(roleName, realmResource))
                .toList();
        userResource.roles().realmLevel().add(rolesRepresentation);
    }

    private RoleRepresentation findRole(String roleName, RealmResource realmResource) {
        try {
            return realmResource.roles().get(roleName).toRepresentation();
        } catch (WebApplicationException ex) {
            throw new IllegalStateException("Role with name '" + roleName + "' does not exist");
        }
    }

    private String createUser(String username, UsersResource usersResource) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(username);
        newUser.setEnabled(true);
        CredentialRepresentation passwordCredential = new CredentialRepresentation();
        passwordCredential.setType(CredentialRepresentation.PASSWORD);
        passwordCredential.setTemporary(false);
        passwordCredential.setValue(username);
        newUser.setCredentials(List.of(passwordCredential));
        Response response = usersResource.create(newUser);
        return CreatedResponseUtil.getCreatedId(response);
    }

    private static void deleteIfExisting(String username, UsersResource usersResource) {
        Optional<UserRepresentation> foundUser =
                usersResource.search(username, 0, 1).stream().findFirst();
        foundUser.ifPresent(user -> usersResource.delete(user.getId()));
    }
}
