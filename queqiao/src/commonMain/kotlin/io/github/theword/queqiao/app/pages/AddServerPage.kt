package io.github.theword.queqiao.app.pages

import io.github.theword.queqiao.app.base.BasePager
import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.Color
import com.tencent.kuikly.core.base.ViewBuilder
import com.tencent.kuikly.core.base.ViewRef
import com.tencent.kuikly.core.views.Input
import com.tencent.kuikly.core.views.InputView
import com.tencent.kuikly.core.views.View
import io.github.theword.queqiao.app.utils.websocket.WebSocketClient

@Page("AddServer")
internal class AddServerPage : BasePager() {
    private lateinit var inputRef: ViewRef<InputView>
    override fun created() {
        super.created()
        val pgData = pagerData

        val websocketClient = WebSocketClient("10.0.2.2", 9090)
        websocketClient.connect()
    }

    override fun body(): ViewBuilder {
        val ctx = this
        return {
            attr {
                backgroundColor(Color.WHITE)
            }
            View {
                attr {
                    marginTop(20f)
                }
            }
            View {
                Input {
                    ref {
                        ctx.inputRef = it
                    }
                    attr {
                        fontSize(30f)
                        fontWeightBold()

                        returnKeyTypeNext()
                        placeholder("请输入ws地址")
                        placeholderColor(Color.YELLOW)

                        color(Color.BLACK)
                        autofocus(true)
                        backgroundColor(Color.RED)
                    }
                }
            }
        }
    }
}