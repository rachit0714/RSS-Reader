package com.example.rssreader

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class RSSItem(

    @Element(name="title")
    val title: String,

    @Element(name="description", required = false)
    val text: String? = null,

    val type: RSSType,

    val mediaUrl: String? = null,

    @field:Element(name = "link", required = false)
    val link: String? = null
)

enum class RSSType {
    TEXT,
    VIDEO,
    IMAGE
}