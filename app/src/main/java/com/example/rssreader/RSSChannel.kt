package com.example.rssreader

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
data class RSSChannel @JvmOverloads constructor(

    @field:Element(name = "title", required = false)
    var title: String? = null,

    @field:Element(name = "link", required = false)
    var link: String? = null,

    @field:ElementList(entry = "item", inline = true, required = false)
    var items: List<RSSItem>? = null
)