package com.buytogheter.controllers;

import com.buytogheter.dtos.PageableDto;
import com.buytogheter.dtos.UserCreateDto;
import com.buytogheter.dtos.UserResponseDto;
import com.buytogheter.dtos.UserUpdateDto;
import com.buytogheter.dtos.mapper.Mapper;
import com.buytogheter.models.User;
import com.buytogheter.repositories.projections.FriendshipRequestProjection;
import com.buytogheter.repositories.projections.UserProjection;
import com.buytogheter.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final Mapper mapper;

    @PostMapping(value = "/create")
    public ResponseEntity<String> create(@RequestBody @Valid UserCreateDto data){
        String result = service.create(mapper.map(data, User.class));
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/search", params = "prefix")
    public ResponseEntity<PageableDto> findByNameOrUsernamePrefix(@RequestParam ("prefix") String prefix, Pageable pageable) {

        Page<UserProjection> result = service.findByNameOrUsernamePrefix(prefix, pageable);

        return ResponseEntity.ok(mapper.map(result));
    }

    @GetMapping(value = "/me")
    public ResponseEntity<UserResponseDto> findUserLogged(){
        User userLogged = service.findUserLogged();
        return ResponseEntity.ok(mapper.map(userLogged, UserResponseDto.class));
    }

    @PutMapping(value = "update")
    public ResponseEntity<UserResponseDto> update(@RequestBody @Valid UserUpdateDto dto) {
        User updatedUser = service.update(mapper.map(dto, User.class));
        return ResponseEntity.ok(mapper.map(updatedUser, UserResponseDto.class));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> delete(@PathVariable String username) {
        service.delete(username);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/send-friend-request", params = "receiver")
    public ResponseEntity<String> sendFriendRequest(@RequestParam ("receiver") String receiver) {
        String result = service.sendFriendRequest(receiver);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/friend-requests")
    public ResponseEntity<Page<FriendshipRequestProjection>> findAllFriendshipRequests(Pageable pageable) {
        Page<FriendshipRequestProjection> requests = service.FindAllFriendshipRequests(pageable);
        return ResponseEntity.ok(requests);
    }

    @PatchMapping(value = "/accept-friend-request", params = "id")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam("id") UUID id) {
        String result = service.acceptFriendRequest(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/reject-friend-request", params = "id")
    public ResponseEntity<String> rejectFriendRequest(@RequestParam ("id") UUID id) {
        String result = service.rejectFriendRequest(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/unfriend", params = "username")
    public ResponseEntity<String> unfriend(@RequestParam ("username") String username) {
        String result = service.unfriend(username);
        return ResponseEntity.ok(result);
    }
}
