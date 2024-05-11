package dev.toy.sse.repository

import org.springframework.stereotype.Repository
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Repository
class EmitterRepository {
    private val emitterMap: MutableMap<String, SseEmitter> = HashMap()

    fun save(number: Int, sseEmitter: SseEmitter): SseEmitter {
        val key = getKey(number)
        emitterMap[key] = sseEmitter
        return sseEmitter
    }

    fun get(number: Int): SseEmitter? {
        val key = getKey(number)
        return emitterMap[key]
    }

    private fun getKey(number: Int): String {
        return "Emitter:$number"
    }
}