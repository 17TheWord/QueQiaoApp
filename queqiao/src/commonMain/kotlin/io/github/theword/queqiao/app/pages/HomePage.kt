package io.github.theword.queqiao.app.pages

import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.Border
import com.tencent.kuikly.core.base.BorderStyle
import com.tencent.kuikly.core.base.Color
import com.tencent.kuikly.core.base.ViewBuilder
import com.tencent.kuikly.core.layout.FlexJustifyContent
import com.tencent.kuikly.core.module.RouterModule
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import com.tencent.kuikly.core.pager.Pager
import com.tencent.kuikly.core.views.View
import com.tencent.kuikly.core.views.compose.Button

@Page("Home")
internal class HomePage : Pager() {
    override fun body(): ViewBuilder {
        val ctx = this
        return {
            View {
                attr {
                    marginTop(20f)
                }
            }
            View {
                attr {
                    // 放置在最右侧
                    flexDirectionRow()
                    justifyContent(FlexJustifyContent.FLEX_END)
                }
                Button {
                    attr {
                        titleAttr {
                            text("Add")
                            color(Color.RED)
                        }
                        size(width = 90f, height = 40f)
                        borderRadius(20f)
                        backgroundColor(Color.GREEN)
                        border(
                            Border(
                                lineWidth = 1f, color = Color.GREEN, lineStyle = BorderStyle.SOLID
                            )
                        )
                    }
                    event {
                        click {
                            ctx.acquireModule<RouterModule>(RouterModule.MODULE_NAME)
                                .openPage(
                                    "AddServer",
                                    JSONObject().apply {
                                        put("test", "test1")
                                    }
                                )
                        }
                    }
                }
            }
        }
    }

}
