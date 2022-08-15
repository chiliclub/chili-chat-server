package com.chiliclub.chilichat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    private String message;

    private Long chatRoomNo;

}
