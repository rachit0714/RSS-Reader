package com.example.rssreader

import kotlinx.coroutines.channels.Channel
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
    data class RSSFeed @JvmOverloads constructor(
        @field:Element(name = "channel", required = false)
        var channel: RSSChannel? = null
    )