package dev.toy.sse.controller

import dev.toy.sse.service.SseService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RestController
class SSeController(
    val sseService: SseService
) {

    @GetMapping("/sse/connect", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun subscribe(): SseEmitter {
        return sseService.connect()
    }

    @GetMapping("/sse/send")
    fun send(): SseEmitter {
        return sseService.eventSend()
    }
}