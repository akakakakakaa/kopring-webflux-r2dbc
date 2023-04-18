package com.mansu.shoppingmall.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import type.ItemSellStatus
import java.time.LocalDateTime

@Table("item")
data class Item (
    @Id var id: Long? = null,
    val name: String,
    val price: Int,
    val stockNumber: Int,
    val itemDetail: String,
    val itemSellStatus: ItemSellStatus,
    @CreatedDate var regTime: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate var updateTime: LocalDateTime = LocalDateTime.now()
)