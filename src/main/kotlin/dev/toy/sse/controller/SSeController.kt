package dev.toy.sse.controller

import dev.toy.sse.service.SseService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import kotlin.random.Random

@RestController
class SSeController(
    val sseService: SseService
) {

    @GetMapping("/sse/connect")
    fun subscribe(): SseEmitter {
        return sseService.connect()
    }

    @GetMapping("/sse/send")
    fun send() {
        val number = Random(10).nextInt()
        sseService.eventSend(number)
    }
}