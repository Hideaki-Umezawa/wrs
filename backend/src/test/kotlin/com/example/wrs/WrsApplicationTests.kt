package com.example.wrs

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.HttpClientErrorException

//テストをランダムなポードで開いている
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WrsApplicationTests(
    @Autowired val restTemplate: TestRestTemplate,
    @LocalServerPort val port: Int
) {
    @Autowired
    private lateinit var repository: WrsRepository

    @BeforeEach
    fun setup() {
        // 各テストは項目が空の状態で始める。
        repository.deleteAll()
    }

    //@Testと書くだけでtestコードと認識する
    @Test
    fun contextLoads() {
    }

    @Test
    fun `最初のテスト`() {
        assertThat(1 + 2, equalTo(3))
    }

    @Test
    fun `GETリクエストはOKステータスを返す`() {
        // localhost/lists に GETリクエスト
        val response = restTemplate.getForEntity("http://localhost:$port/api/lists", String::class.java)
        // レスポンスのステータスコードは OK である。
        assertThat(response.statusCode, equalTo(HttpStatus.OK))
    }

    @Test
    fun `GETリクエストは空のlistsリストを返す`() {
        // localhost/lists に GETリクエストを送り、レスポンスを WrsEntity の配列として解釈する。
        val response = restTemplate.getForEntity("http://localhost:$port/api/lists", Array<WrsEntity>::class.java)
        val lists = response.body!!

        // 配列は0個の要素をもつこと。
        assertThat(lists.size, equalTo(0))
    }

    @Test
    fun `POSTリクエストはOKステータスを返す`() {
        // localhost/lists に POSTリクエストを送る。このときのボディは {"text": "hello"}
        val request = WrsRequest(text = "こんにちは", originLat = 0.0, originLng = 0.0, destLat = 0.0, destLng = 0.0)
        val response = restTemplate.postForEntity("http://localhost:$port/api/lists", request, String::class.java)
        // レスポンスのステータスコードは OK であること。
        assertThat(response.statusCode, equalTo(HttpStatus.OK))
    }


    @Test
    fun `POSTリクエストはlistsオブジェクトを格納する`() {
        // localhost/listsにPOSTリクエストを送る。このときのボディは {"text": "こんにちは"}
        val request = WrsRequest(text = "こんにちは", originLat = 0.0, originLng = 0.0, destLat = 0.0, destLng = 0.0)
        restTemplate.postForEntity("http://localhost:$port/api/lists", request, String::class.java)
        // localhost/lists に GETリクエストを送り、レスポンスを WrsEntity の配列として解釈する。
        // このときのレスポンスを lists として記憶。
        val response = restTemplate.getForEntity("http://localhost:$port/api/lists", Array<WrsEntity>::class.java)
        val lists = response.body!!

        // 配列 lists の長さは 1。
        assertThat(lists.size, equalTo(1))
        // 配列 lists[0] には "こんにちは" をもつWrsEntity が含まれている。
        assertThat(lists[0].text, equalTo("こんにちは"))
        assertThat(lists[0].originLat, equalTo(0.0))
        assertThat(lists[0].originLng, equalTo(0.0))
        assertThat(lists[0].destLat, equalTo(0.0))
        assertThat(lists[0].destLng, equalTo(0.0))
    }

    //他テストに影響されて
    @Test
    fun `POSTリクエストは追加したidを返す`() {
        val request = WrsRequest(text = "こんにちは", originLat = 0.0, originLng = 0.0, destLat = 0.0, destLng = 0.0)
        val postResponse = restTemplate.postForEntity("http://localhost:$port/api/lists", request, String::class.java)

        assertThat(postResponse.statusCode, equalTo(HttpStatus.OK))
        assertThat(postResponse.body?.toLongOrNull(), notNullValue())  // 「数値が返ること」だけを確認
    }

    @Test
    fun `特定の項目をIDを指定してGETできる`() {
        //1回目(ダミー)
        val firstRequest =
            WrsRequest(text = "こんにちは", originLat = 0.0, originLng = 0.0, destLat = 0.0, destLng = 0.0)
        restTemplate.postForEntity("http://localhost:$port/api/lists", firstRequest, Long::class.java)

        //2回目
        val secondRequest =
            WrsRequest(text = "こんにちは", originLat = 1.1, originLng = 1.1, destLat = 1.1, destLng = 1.1)
        val postResponse =
            restTemplate.postForEntity("http://localhost:$port/api/lists", secondRequest, Long::class.java)
        val postResponseId = postResponse.body
        println("📍📍📍📍📍📍${postResponseId}")
        //id2をgetする返り血はオブジェクトだからWrsEntityを使用する
        val response =
            restTemplate.getForEntity("http://localhost:$port/api/lists/$postResponseId", WrsEntity::class.java)
        val lists = response.body!!


        //各要素で比較しないと
        assertThat(lists.id, equalTo(postResponseId))
        assertThat(lists.text, equalTo(secondRequest.text))
        assertThat(lists.originLat, equalTo(secondRequest.originLat))
        assertThat(lists.originLng, equalTo(secondRequest.originLng))
        assertThat(lists.destLat, equalTo(secondRequest.destLat))
        assertThat(lists.destLng, equalTo(secondRequest.destLng))
    }


    @Test
    fun `存在しないIDでGETするとステータス404を返す`() {
        val response = restTemplate.getForEntity("http://localhost:$port/api/lists/99999", String::class.java)
        // ステータスを直接チェック
        assertThat(response.statusCode, equalTo(HttpStatus.NOT_FOUND))
    }


    @Test
    fun `DELETEでIDを指定して削除できる`() {

        val secondRequest = WrsRequest(
            text = "こんにちは",
            originLat = 1.1,
            originLng = 1.1,
            destLat = 1.1,
            destLng = 1.1
        )
        val postResponse = restTemplate.postForEntity(
            "http://localhost:$port/api/lists",
            secondRequest,
            Long::class.java
        )
        val id = postResponse.body!!

        //DELETE
        restTemplate.delete("http://localhost:$port/api/lists/$id")

        // 404
        val getResponse = restTemplate.exchange(
            "http://localhost:$port/api/lists/$id",
            HttpMethod.GET,
            null,
            String::class.java
        )
        assertThat(getResponse.statusCode, equalTo(HttpStatus.NOT_FOUND))
    }

}
