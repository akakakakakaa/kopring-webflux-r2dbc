package com.mansu.shoppingmall.repository

import com.mansu.shoppingmall.model.Item
import io.kotest.assertions.fail
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.boot.test.context.SpringBootTest
import type.ItemSellStatus
import java.time.LocalDateTime


@SpringBootTest
class ItemRepositoryTest(@Autowired itemRepo: ItemRepository): FunSpec({
    extension(SpringExtension)

    beforeSpec {

        for (i in 0..10) {
            val item: Item = Item(
                name = "테스트 상품$i",
                price = 10000 + i,
                itemDetail = "테스트 상품 상세 설명$i",
                itemSellStatus = ItemSellStatus.SELL,
                stockNumber = 100,
                regTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now()
            )
            val saved = itemRepo.save(item)

            saved.name shouldNotBe null
            saved.price shouldBe item.price
            saved.itemDetail shouldBe item.itemDetail
            saved.itemSellStatus shouldBe item.itemSellStatus
            saved.stockNumber shouldBe item.stockNumber
            saved.regTime shouldBe item.regTime
            saved.updateTime shouldBe item.updateTime
        }
    }

    afterSpec {
        itemRepo.deleteAll()
    }

    test("상품명 조회 테스트") {
        val itemList: List<Item> = itemRepo.findByName("테스트 상품1")
        itemList.size shouldBe 1
    }

    test("상품명, 상품상세설명 or 테스트") {
        val itemList: List<Item> = itemRepo.findByNameOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5")
        itemList.size shouldBe 2
    }

    test("가격 LessThan 테스트") {
        val itemList: List<Item> = itemRepo.findByPriceLessThan(10005)
        itemList.size shouldBe 5
    }

    test("가격 내림차순 조회 테스트") {
        val itemList: List<Item> = itemRepo.findByPriceLessThanOrderByPriceDesc(10005)
        itemList.size shouldBe 5

        var prevPrice = Int.MAX_VALUE
        for (item in itemList) {
            if (item.price >= prevPrice) {
                fail("List not ordered by price in descending order")
            }
            prevPrice = item.price
        }
    }

    test("@Query를 이용한 상품 조회 테스트") {
        val itemList: List<Item> = itemRepo.findByItemDetail("테스트 상품 상세 설명")
        itemList.size shouldBe 11

        var prevPrice = Int.MAX_VALUE
        for (item in itemList) {
            if (item.price >= prevPrice) {
                fail("List not ordered by price in descending order")
            }
            prevPrice = item.price
        }
    }
})