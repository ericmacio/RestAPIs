package com.eric.restapi.user_service.controller;

import com.eric.restapi.user_service.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name="UserControllerApi")
@RequestMapping("/api/v1/users")
public interface UserApi {

    /**
     * Sample usage: "curl $HOST:$PORT/product/1".
     *
     * @return the list of users
     */
    @GetMapping(produces = "application/json")
    ResponseEntity<List<User>> getUsers();

    /**
     * Sample usage: "curl $HOST:$PORT/users/1".
     *
     * @param userId ID of the user
     * @return the user info, if found, else null
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<User> getUser(@PathVariable("id") Long userId);

    /**
     * Sample usage, see below.
     * curl -X POST $HOST:$PORT/users \
     *   -H "Content-Type: application/json" --data \
     *   '{"first_name":"eric","last_name":"macio","email":"eric.macio@gmail.com"}'
     *
     * @param user A JSON representation of the new user
     * @return A JSON representation of the newly created user
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<User> postUser(@RequestBody @Valid UserDTO user);

    /**
     * Sample usage, see below.
     * curl -X PUT $HOST:$PORT/users/1 \
     *   -H "Content-Type: application/json" --data \
     *   '{"first_name":"eric","last_name":"macio","email":"eric.macio@gmail.com"}'
     *
     * @param id ID of the user
     * @param updatedUser A JSON representation of the user to be updated
     * @return A JSON representation of the updated user, if found, else of the newly created user
     */
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    ResponseEntity<User> putUser(@PathVariable Long id, @RequestBody @Valid UserDTO updatedUser);

    /**
     * Sample usage, see below.
     * curl -X PATCH $HOST:$PORT/users/1 \
     *   -H "Content-Type: application/json" --data \
     *   '{"first_name":"eric","last_name":"macio","email":"eric.macio@gmail.com"}'
     *
     * @param id ID of the user
     * @param user A JSON representation of the user to be patched
     * @return A JSON representation of the patched user, if found, else null
     */
    @PatchMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody UserDTO user);

    /**
     * Sample usage,  "curl -X DELETE $HOST:$PORT/product/1".
     *
     * @param id ID of the user to be deleted
     */
    @DeleteMapping(value = "/{id}")
    void deleteUser(@PathVariable Long id);

}
