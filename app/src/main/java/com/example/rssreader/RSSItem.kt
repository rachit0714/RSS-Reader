package com.example.rssreader

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
class RSSItem(

    @field:Element(name = "title", required = false)
    var title: String = "",

    @field:Element(name="description", required = false)
    var text: String = "",

    @field:Element(name = "link", required = false)
    var link: String? = null,

    @org.simpleframework.xml.Transient
    var type: RSSType = RSSType.TEXT,

    @org.simpleframework.xml.Transient
    var mediaUrl: String? = null

)

enum class RSSType {
    TEXT,
    VIDEO,
    IMAGE
}