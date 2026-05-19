package ru.stellarburgers.utils;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserApi {

    private static final String BASE_URL =
            "https://qa-stellarburgers.education-services.ru/api";

    @Step("Создать пользователя через API: email={email}")
    public static void createUser(String name, String email, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("name", name);
        body.put("email", email);
        body.put("password", password);

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(BASE_URL + "/auth/register");
    }

    @Step("Получить accessToken для пользователя: email={email}")
    public static String getAccessToken(String email, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(BASE_URL + "/auth/login")
                .then()
                .extract()
                .path("accessToken");
    }

    @Step("Удалить пользователя через API")
    public static void deleteUser(String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) return;

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .when()
                .delete(BASE_URL + "/auth/user");
    }
}
