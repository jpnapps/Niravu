package com.jpndev.niravu.register

import com.jpndev.niravu.MWeb

data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val emailError: Int? = null,
    val isDataValid: Boolean = false
)
data class RespLogin(
    val data: Data?=null,
    val in_app: Boolean=true,
    val web_url: String ?=null,
    val browser_url: String ?=null,
    val web_properties: MWeb?=null,
    val status: Int=0,
    val error_code: Int=0,
    val errors: Errors?=null
)

data class RespAccessToekn(
    val `data`: AccessToeknData?=null,
    val status: Int=0,
    val error_code: Int=0,
    val in_app: Boolean=true,
    val web_properties: MWeb?=null,
    val web_url: String ?=null,
    val browser_url: String ?=null,
    val errors: Errors?=null
)

data class  RespRegister(
    val `data`: UserData?=null,
    val status: Int=0,
    val error_code: Int=0,
    val in_app: Boolean=true,
    val web_properties: MWeb?=null,
    val web_url: String ?=null,
    val browser_url: String ?=null,
    val errors: Errors?=null
)

data class Errors(
    val password: List<String> ?=null,
    val username: List<String> ?=null
)
data class Data(
    val authorization_code: String?=null,
    val expires_at: Int?=null
)
data class AccessToeknData(
    val access_token: String?=null,
    val expires_at: Int?=null
)

data class UserData(
    val username: String?=null,
    val id: Int?=null,
    val status: Int?=null,
    val email: String?=null
)