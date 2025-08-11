package com.example.rssreader

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
data class Channel @JvmOverloads constructor(
    @field:ElementList(entry = "item", inline = true)
    var items: List<RSSItem>? = null,

    @field:Element(name = "title")
    var title: String = "",

    @field:Element(name = "link")
    var link: String = "",
)