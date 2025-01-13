//package com.springboot.hello;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/v1/messages")
//public class MessageController {
//    private final MessageService messageService;
//    private final MessageMapper messageMapper;
//
//    public MessageController(MessageService messageService, MessageMapper messageMapper) {
//        this.messageService = messageService;
//        this.messageMapper = messageMapper;
//
//    }
//
//    @PostMapping
//    public ResponseEntity postMessage(@Valid @RequestBody MessagePostDto messagePostDto) {
//        Message message = messageService.createMessage(messageMapper.messageDtoToMessage(messagePostDto));
//
//        return ResponseEntity.ok(messageMapper.messageToMessageResponseDto(message));
//    }
//}
