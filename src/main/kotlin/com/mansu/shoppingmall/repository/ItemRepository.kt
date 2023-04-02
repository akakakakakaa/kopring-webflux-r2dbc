package com.mansu.shoppingmall.repository

import com.mansu.shoppingmall.model.Item
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository: CoroutineCrudRepository<Item, Long> {
    suspend fun findByName(name: String): List<Item>

    suspend fun findByNameOrItemDetail(name: String, itemDetail: String): List<Item>

    suspend fun findByPriceLessThan(price: Int): List<Item>

    suspend fun findByPriceLessThanOrderByPriceDesc(price: Int): List<Item>

    @Query("SELECT * FROM item i WHERE i.item_detail LIKE CONCAT('%', :itemDetail, '%') ORDER BY i.price DESC")
    suspend fun findByItemDetail(@Param("itemDetail") itemDetail: String): List<Item>
}