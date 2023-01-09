package com.example.daggerhilt.util

import com.example.daggerhilt.database.PostEntity
import com.example.daggerhilt.model.Post

fun Post.toPostEntity(): PostEntity {
    return PostEntity(
        title = title,
        body = body
    )
}
fun PostEntity.toPost(): Post {
    return Post(
        title = title,
        body = body
    )
}
