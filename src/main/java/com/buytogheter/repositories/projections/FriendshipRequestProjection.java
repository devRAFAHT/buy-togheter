package com.buytogheter.repositories.projections;

import com.buytogheter.enums.RequestStatus;

public interface FriendshipRequestProjection {

    UserProjection getSender();
    RequestStatus getStatus();

}
