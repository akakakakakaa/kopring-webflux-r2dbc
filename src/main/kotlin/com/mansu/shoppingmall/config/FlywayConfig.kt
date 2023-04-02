package com.mansu.shoppingmall.config

import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.flyway.FlywayProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlywayConfig {

    @Bean
    fun flyway(flywayProperties: FlywayProperties): Flyway {
        val flyway = Flyway.configure()
            .baselineOnMigrate(flywayProperties.isBaselineOnMigrate)
            .baselineVersion(flywayProperties.baselineVersion)
            .dataSource(flywayProperties.url, flywayProperties.user, flywayProperties.password)
            .load()

        // PostgreSQL은 자동으로 실패한 DDL을 롤백해줍니다.
        // 그래서 repair()을 수행하면 이미 적용된 마이그레이션의 체크섬을 사용 가능한 마이그레이션의 체크섬으로 재정렬합니다.
        flyway.repair()
        flyway.migrate()
        return flyway
    }

    @Bean
    fun flywayProperties(): FlywayProperties {
        return FlywayProperties()
    }
}