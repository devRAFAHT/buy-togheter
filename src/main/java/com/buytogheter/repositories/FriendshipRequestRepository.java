package com.buytogheter.repositories;

import com.buytogheter.enums.RequestStatus;
import com.buytogheter.models.FriendshipRequest;
import com.buytogheter.models.User;
import com.buytogheter.repositories.projections.FriendshipRequestProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, UUID> {

    Optional<FriendshipRequest> findBySenderAndReceiver(User sender, User receiver);
    @Query("SELECT f FROM FriendshipRequest f WHERE (f.sender = :user1 AND f.receiver = :user2) OR (f.sender = :user2 AND f.receiver = :user1)")
    Optional<FriendshipRequest> findFriendshipBetween(@Param("user1") User user1, @Param("user2") User user2);
    Page<FriendshipRequestProjection> findAllByReceiverAndStatus(User receiver, RequestStatus status, Pageable pageable);
}
