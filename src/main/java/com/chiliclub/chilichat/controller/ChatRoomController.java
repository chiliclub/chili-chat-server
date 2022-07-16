package com.chiliclub.chilichat.controller;

import com.chiliclub.chilichat.model.ChatRoomCreateRequest;
import com.chiliclub.chilichat.model.ChatRoomFindResponse;
import com.chiliclub.chilichat.model.ChatRoomUpdateRequest;
import com.chiliclub.chilichat.model.ChatRoomUpdateResponse;
import com.chiliclub.chilichat.service.ChatRoomService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat-rooms")
@RequiredArgsConstructor
@Validated
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @ApiOperation(value = "채팅방 생성")
    @PostMapping("")
    public ResponseEntity<Long> chatRoomAdd(
            @RequestBody ChatRoomCreateRequest chatRoomCreateRequest
    ) {
        Long chatRoomId = chatRoomService.addChatRoom(chatRoomCreateRequest);

        return ResponseEntity.ok(chatRoomId);
    }

    @ApiOperation(value = "채팅방 목록 조회")
    @GetMapping("")
    public ResponseEntity<List<ChatRoomFindResponse>> chatRoomList() {
        return ResponseEntity.ok(chatRoomService.findChatRoomList());
    }

    @ApiOperation(value = "채팅방 삭제")
    @DeleteMapping("/{chatRoomNo}")
    public ResponseEntity<Boolean> chatRoomRemove(@PathVariable Long chatRoomNo) {
        chatRoomService.removeChatRoom(chatRoomNo);

        return ResponseEntity.ok(true);
    }

    @ApiOperation(value = "채팅방 정보 수정")
    @PutMapping("/{chatRoomNo}")
    public ResponseEntity<ChatRoomUpdateResponse> chatRoomModify(
            @PathVariable Long chatRoomNo,
            @RequestBody ChatRoomUpdateRequest chatRoomUpdateRequest
            ) {

        ChatRoomUpdateResponse chatRoomUpdateResponse = chatRoomService.modifyChatRoom(chatRoomNo, chatRoomUpdateRequest);

        return ResponseEntity.ok(chatRoomUpdateResponse);
    }
}
