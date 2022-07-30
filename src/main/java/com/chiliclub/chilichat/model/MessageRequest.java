package com.chiliclub.chilichat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageRequest {

    private String message;

    private String chatRoomNo;

}
