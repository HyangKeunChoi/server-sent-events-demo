package dev.toy.sse.service

import dev.toy.sse.repository.EmitterRepository
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.RuntimeException

@Service
class SseService(
    private val emitterRepository: EmitterRepository
) {

    fun connect(): SseEmitter {
        val sseEmitter = SseEmitter(DEFAULT_TIMEOUT)
        sseEmitter.onCompletion{ println("complete") }
        sseEmitter.onTimeout{ print("timeout") }

        try {
            sseEmitter.send(SseEmitter.event().id("id").name(EVENT).data(Random().nextInt()))
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

        return sseEmitter
    }

    companion object {
        const val DEFAULT_TIMEOUT = 10L * 1000
        const val EVENT = "event"
    }

    fun eventSend(number: Int) {
        emitterRepository.save(number, SseEmitter())
        val sseEmitter = emitterRepository.get(number) ?: throw IllegalArgumentException("없는 number")
        try {
            sseEmitter.send(SseEmitter.event().id("id").name(EVENT).data("SUCCESS"))
        } catch (e: IOException) {
            throw IOException("오류 발생")
        }
    }
}