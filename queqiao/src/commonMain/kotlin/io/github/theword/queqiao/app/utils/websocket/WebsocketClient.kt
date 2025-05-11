package io.github.theword.queqiao.app.utils.websocket

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.header
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.runBlocking


/**
 * WebSocket Client for Kotlin/Native
 * @param url Default WebSocket Server URL (Default: 127.0.0.1)
 * @param port Default WebSocket Server Port (Default: 8080)
 * @param serverName Default Server Name (Default: Server)
 */
class WebSocketClient(
    private val url: String = "127.0.0.1",
    private val port: Int = 8080,
    private val serverName: String = "Server"
) {
    private lateinit var client: HttpClient
    private lateinit var webSocketSession: DefaultClientWebSocketSession

    init {
        Log.i(this.javaClass.name, "Initializing WebSocketClient for @$serverName to [$url:$port]")
        client = HttpClient(CIO) {
            install(WebSockets) {
                Log.d(this.javaClass.name, "Install WebSockets")
                pingInterval = 15000
                Log.d(this.javaClass.name, "Set pingInterval: $pingInterval")
            }
            engine {
                requestTimeout = 60000
                Log.d(this.javaClass.name, "Set requestTimeout: $requestTimeout")
            }
        }
    }

    fun connect() {
        Log.i(this.javaClass.name, "Connecting @$serverName to [$url:$port]")
        runBlocking {
            client.webSocket(
                method = HttpMethod.Get,
                host = url,
                port = port,
                request = {
                    header("x-self-name", serverName)
                },
            ) {
                webSocketSession = this
                Log.i(this.javaClass.name, "Connected @$serverName to [$url:$port]")
                while (true) {
                    val frame = incoming.receive()
                    if (frame is Frame.Text) {
                        val text = frame.readText()
                        Log.d(this.javaClass.name, "Received message from [$url:$port] : $text")
                        handleMessage(text)
                    }
                }
            }
        }
    }

    // 封装发送方法
    suspend fun sendMessage(text: String) {
        webSocketSession.send(Frame.Text(text))
        Log.d(this.javaClass.name, "Sent message to @$serverName [$url:$port] : $text")
    }

    // 关闭连接
    suspend fun disconnect() {
        webSocketSession.close()
        Log.i(this.javaClass.name, "Disconnected @$serverName from [$url:$port]")
        client.close()
        Log.i(this.javaClass.name, "Closed WebSocketClient for @$serverName to [$url:$port]")
    }

    private fun handleMessage(text: String) {
        Log.i(this.javaClass.name, "Handling message from [$url:$port] : $text")
    }
}
