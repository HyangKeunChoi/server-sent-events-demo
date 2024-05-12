package dev.toy.sse.service

import dev.toy.sse.repository.EmitterRepository
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.lang.IllegalArgumentException
import kotlin.RuntimeException

@Service
class SseService(
    private val emitterRepository: EmitterRepository
) {
    fun connect(): SseEmitter {
        val sseEmitter = SseEmitter(DEFAULT_TIMEOUT)
        sseEmitter.onCompletion { println("complete") }
        sseEmitter.onTimeout { print("timeout") }

        try {
            sseEmitter.send(SseEmitter.event().id("id").name(EVENT).data("connected"))
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        emitterRepository.save(1, sseEmitter)
        return sseEmitter
    }

    companion object {
        const val DEFAULT_TIMEOUT = 10L * 1000
        const val EVENT = "event"
    }

    fun eventSend(): SseEmitter {
        val sseEmitter = emitterRepository.get(1) ?: throw IllegalArgumentException("wrong number")
        sseEmitter.let {
            try {
                it.send(SseEmitter.event().id("id").name("event send").data("SUCCESS"))
            } catch (e: IOException) {
                throw IOException("Failed to send event")
            }
        }
        return sseEmitter
    }
}