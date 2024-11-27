package com.alexgutjahr.corsdemo

import com.alexgutjahr.corsdemo.CorrectCorsSetup.CorrectNamingConfig
import com.alexgutjahr.corsdemo.IncorrectCorsSetup.IncorrectNamingConfig
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class CorsTest {

  @Autowired
  private lateinit var client: WebTestClient

  @Test
  fun `given valid request then CORS header are present`() {
    client.options()
      .uri("/ping")
      .header("Origin", "https://example.org")
      .header("Access-Control-Request-Method", "GET")
      .exchange()
      .expectHeader().valueEquals("Access-Control-Allow-Origin", "https://example.org")
      .expectHeader().valueEquals("Access-Control-Allow-Methods", "GET")
  }
}

/**
 * Correct Naming Setup
 */
@Import(CorrectNamingConfig::class)
class CorrectCorsSetup : CorsTest() {

  class CorrectNamingConfig {
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
      val config = CorsConfiguration()
      config.allowedOrigins = listOf("https://example.org")
      config.allowedMethods = listOf("*")
      config.allowedHeaders = listOf("*")

      val source = UrlBasedCorsConfigurationSource()
      source.registerCorsConfiguration("/**", config)

      return source
    }
  }
}

/**
 * Incorrect Naming Setup
 */
@Import(IncorrectNamingConfig::class)
class IncorrectCorsSetup : CorsTest() {
  class IncorrectNamingConfig {
    @Bean
    fun someArbitraryName(): CorsConfigurationSource {
      val config = CorsConfiguration()
      config.allowedOrigins = listOf("https://example.org")
      config.allowedMethods = listOf("*")
      config.allowedHeaders = listOf("*")

      val source = UrlBasedCorsConfigurationSource()
      source.registerCorsConfiguration("/**", config)

      return source
    }
  }
}
