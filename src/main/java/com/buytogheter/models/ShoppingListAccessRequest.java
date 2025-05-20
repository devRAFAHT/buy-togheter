package com.buytogheter.models;

import com.buytogheter.enums.AccessRequestType;
import com.buytogheter.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_shopping_list_access_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListAccessRequest extends BaseEntity{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccessRequestType accessRequestType;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shopping_list_id", nullable = false)
    private ShoppingList shoppingList;
}
