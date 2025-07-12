package com.buytogheter.services;

import com.buytogheter.enums.RequestStatus;
import com.buytogheter.exceptions.*;
import com.buytogheter.models.FriendshipRequest;
import com.buytogheter.models.User;
import com.buytogheter.repositories.FriendshipRequestRepository;
import com.buytogheter.repositories.UserRepository;
import com.buytogheter.repositories.projections.FriendshipRequestProjection;
import com.buytogheter.repositories.projections.UserProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.UserUtil;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final FriendshipRequestRepository friendshipRequestRepository;

    @Transactional
    public String create(User user) {
        log.info("Creating user with username: {}", user.getUsername());
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user = repository.save(user);
            log.info("User created successfully: {}", user.getUsername());
            return "Conta criada com sucesso.";

        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation during user creation: {}", e.getMessage());
            if (e.getCause() instanceof ConstraintViolationException constraintEx) {
                String constraint = constraintEx.getConstraintName();
                if ("uk_username".equals(constraint)) {
                    throw new UniqueViolationException("O nome de usuário já está em uso.");
                } else if ("uk_email".equals(constraint)) {
                    throw new UniqueViolationException("O e-mail já está em uso.");
                }
                throw new UniqueViolationException("Violação de unicidade.");
            }
            throw new DatabaseException("Violação de integridade não identificada.");
        }
    }

    @Transactional(readOnly = true)
    public Page<UserProjection> findByNameOrUsernamePrefix(String prefix, Pageable pageable) {
        log.info("Searching users by prefix: {}", prefix);
        return repository.findByNameContainingIgnoreCaseOrUsernameContainingIgnoreCase(prefix, prefix, pageable);
    }

    @Transactional(readOnly = true)
    public User findById(UUID id) {
        log.info("Finding user by id: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found with id: {}", id);
                    return new ResourceNotFoundException("Usuário com o id '" + id + "' não encontrado!");
                });
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        log.info("Finding user by username: {}", username);
        return repository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User not found with username: {}", username);
                    return new ResourceNotFoundException("Usuário com o username '" + username + "' não encontrado!");
                });
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        log.info("Finding user by email: {}", email);
        return repository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User not found with email: {}", email);
                    return new ResourceNotFoundException("Usuário com o email '" + email + "' não encontrado!");
                });
    }

    @Transactional(readOnly = true)
    public User.Role findRoleByUsername(String username) {
        log.info("Finding role for username: {}", username);
        return repository.findRoleByUsername(username);
    }

    @Transactional(readOnly = true)
    public User findUserLogged() {
        String loggedUsername = UserUtil.getLoggedInUsername();
        log.info("Retrieving logged-in user: {}", loggedUsername);
        return findByUsername(loggedUsername);
    }

    @Transactional
    public User update(User data) {
        User user = findUserLogged();
        log.info("Updating user: {}", user.getUsername());
        user.setName(data.getName());
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setAvatar(data.getAvatar());
        User updated = repository.save(user);
        log.info("User updated successfully: {}", updated.getUsername());
        return updated;
    }

    @Transactional
    public void delete(String username) {
        log.info("Deleting user with username: {}", username);
        User user = findByUsername(username);
        try {
            repository.delete(user);
            log.info("User deleted successfully: {}", username);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation during user deletion: {}", e.getMessage());
            throw new DatabaseException("Erro de integridade");
        }
    }

    @Transactional
    public String sendFriendRequest(String receiverUsername) {
        User sender = findUserLogged();
        log.info("User '{}' sending friend request to '{}'", sender.getUsername(), receiverUsername);
        User receiver = findByUsername(receiverUsername);

        if (sender.equals(receiver)) {
            log.warn("User '{}' attempted to send request to self", sender.getUsername());
            throw new InvalidRequestOperationException("Não é possível enviar uma solicitação para si mesmo.");
        }

        Optional<FriendshipRequest> optionalRequest = friendshipRequestRepository.findBySenderAndReceiver(sender, receiver);
        if (optionalRequest.isPresent()) {
            FriendshipRequest friendshipRequest = optionalRequest.get();
            log.info("Existing friendship request found between '{}' and '{}' with status {}", sender.getUsername(), receiverUsername, friendshipRequest.getStatus());
            if (friendshipRequest.getStatus().equals(RequestStatus.PENDING)) {
                throw new RequestAlreadySentException("Solicitação de amizade já enviada.");
            }
            if (friendshipRequest.getStatus().equals(RequestStatus.ACCEPTED)) {
                throw new RequestAlreadyAcceptedException("Sua solicitação de amizade já foi aceita.");
            }
        }

        FriendshipRequest newRequest = new FriendshipRequest(RequestStatus.PENDING, sender, receiver);
        friendshipRequestRepository.save(newRequest);
        log.info("Friend request created with id {}", newRequest.getId());

        return "Solicitação de amizade enviada.";
    }

    @Transactional(readOnly = true)
    public Page<FriendshipRequestProjection> FindAllFriendshipRequests(Pageable pageable){
        User userLogged = findUserLogged();
        return friendshipRequestRepository.findAllByReceiverAndStatus(userLogged, RequestStatus.PENDING, pageable);
    }

    @Transactional
    public String acceptFriendRequest(UUID id) {
        Optional<FriendshipRequest> friendshipRequest = friendshipRequestRepository.findById(id);
        User userLogged = findUserLogged();

        if (friendshipRequest.isEmpty()) {
            log.warn("Friendship request not found for id: {}", id);
            throw new InvalidRequestOperationException("Solicitação não encontrada.");
        }

        FriendshipRequest request = friendshipRequest.get();
        if (!request.getReceiver().equals(userLogged)) {
            log.warn("User '{}' is not the receiver of request {}", userLogged.getUsername(), id);
            throw new InvalidRequestOperationException("Apenas o usuário que recebeu a solicitação pode aceitá-la.");
        }

        if (request.getStatus().equals(RequestStatus.ACCEPTED)) {
            throw new InvalidRequestOperationException("Essa solicitação já foi aceita.");
        }

        friendshipRequest.get().setStatus(RequestStatus.ACCEPTED);
        friendshipRequestRepository.save(request);
        log.info("Friendship request {} accept", id);

        return "Solicitação de amizade aceita com sucesso.";
    }

    @Transactional
    public String rejectFriendRequest(UUID id) {
        log.info("Rejecting friend request with id: {}", id);
        Optional<FriendshipRequest> friendshipRequest = friendshipRequestRepository.findById(id);
        User userLogged = findUserLogged();

        if (friendshipRequest.isEmpty()) {
            log.warn("Friendship request not found for id: {}", id);
            throw new InvalidRequestOperationException("Solicitação não encontrada.");
        }

        FriendshipRequest request = friendshipRequest.get();
        if (!request.getReceiver().equals(userLogged)) {
            log.warn("User '{}' is not the receiver of request {}", userLogged.getUsername(), id);
            throw new InvalidRequestOperationException("Apenas o usuário que recebeu a solicitação pode rejeitá-la.");
        }

        if (request.getStatus().equals(RequestStatus.ACCEPTED)) {
            throw new InvalidRequestOperationException("Não é possível rejeitar uma solicitação que já foi aceita");
        }

        friendshipRequestRepository.delete(request);
        log.info("Friendship request {} rejected and deleted", id);

        return "Solicitação de amizade rejeitada com sucesso.";
    }

    @Transactional
    public String unfriend(String friendToRemoveUsername) {
        User userLogged = findUserLogged();
        log.info("User '{}' unfriending user '{}'", userLogged.getUsername(), friendToRemoveUsername);
        User friendToRemove = findByUsername(friendToRemoveUsername);
        Optional<FriendshipRequest> optional = friendshipRequestRepository.findFriendshipBetween(
                userLogged, friendToRemove);
        
        FriendshipRequest friendship = optional.orElseThrow(() -> {
            log.warn("Friendship not found between '{}' and '{}'", userLogged.getUsername(), friendToRemoveUsername);
            return new ResourceNotFoundException("Amizade não encontrada.");
        });

        if (!friendship.getStatus().equals(RequestStatus.ACCEPTED)) {
            log.warn("Attempt to unfriend when status is not ACCEPTED: {}", friendship.getStatus());
            throw new InvalidRequestOperationException("Não é possível desfazer uma amizade inexistente.");
        }

        friendshipRequestRepository.delete(friendship);
        log.info("Friendship between '{}' and '{}' removed", userLogged.getUsername(), friendToRemoveUsername);

        return "Amizade desfeita com sucesso.";
    }
}