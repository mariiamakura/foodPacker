package com.foody.request;

import lombok.Data;

@Data
public class UpdateCartIteamRequest {
    private Long cartItemId;
    private int quantity;
}
