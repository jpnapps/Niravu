package com.jpndev.niravu

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

class Model {
}


data class MUpdateData(
    val is_force_update: Boolean=false,
    val first_url: String?=null,
    val dummy_first_url: String?=null,
    val update_title: String?=null,
    val update_message: String?=null,
    val version_name: String?=null,
    val version_code: Int=1,
    val message: String?=null,
    val errors: Any?=null,
    val browser_url: String?=null,
    val web_url: String?=null,
    val in_app: Boolean=true,
    val web_properties: MWeb?=null,
    val status: Boolean=true,
    val app: MApp?=null
)


data class MApp(
    val app_name: String,
    val bg_detail_1: String?=null,
    val bg_detail_2: String?=null,
    val bg_home_1: String?=null,
    val bg_home_2: String?=null,
    val bg_list_1: String?=null,
    val bg_list_2: String?=null,
    val bg_list_3: String?=null,
    val bg_splash_1: String?=null,
    val bg_splash_2: String?=null,
    val developer: String,
    val icon_app_1: String?=null,
    val icon_app_2: String?=null,
    val icon_developer_1: String?=null,
    val icon_developer_2: String?=null,

    val light_mode: ThemeMode?=null,
    val dark_mode: ThemeMode?=null,
    val system_mode: ThemeMode?=null
)

@Parcelize
data class ThemeMode(
    val app_name: String,
    val bg_detail_1: String?=null,
    val bg_detail_2: String?=null,
    val bg_home_1: String?=null,
    val bg_home_2: String?=null,
    val bg_list_1: String?=null,
    val bg_list_2: String?=null,
    val bg_list_3: String?=null,
    val bg_splash_1: String?=null,
    val bg_splash_2: String?=null,
    val developer: String,
    val icon_app_1: String?=null,
    val icon_app_2: String?=null,
    val icon_developer_1: String?=null,
    val icon_developer_2: String?=null,

    val text_color_1: String?=null,
    val text_color_2: String?=null,
    val text_color_3: String?=null,
    val text_color_4: String?=null,
    val text_color_5: String?=null,

    val bg_color_1: String?=null,
    val bg_color_2: String?=null,
    val bg_home_color_1: String?=null,
    val bg_home_color_2: String?=null,
    val bg_detail_color_1: String?=null,
    val bg_detail_color_2: String?=null,


    val bg_list_color_1: String?=null,
    val bg_list_color_2: String?=null,

    val bg_rcv_item_color_1: String?=null,
    val bg_rcv_item_color_2: String?=null,

    val bg_card_color_1: String?=null,
    val bg_card_color_2: String?=null,

    val bg_view_color_1: String?=null,
    val bg_view_color_2: String?=null,

    val bg_border_color_1: String?=null,
    val bg_border_color_2: String?=null,

    val tint_color_1: String?=null,
    val tint_color_2: String?=null,


    var type_0: ThemeType?=null,
    var type_1: ThemeType?=null,
    var type_2: ThemeType?=null,

    var type_3: ThemeType?=null,
    var type_4: ThemeType?=null,

    var type_5: ThemeType?=null,
    var type_6: ThemeType?=null,

    var type_7: ThemeType?=null,
    var type_8: ThemeType?=null,

    var type_9: ThemeType?=null,
    var type_10: ThemeType?=null,

    var type_11: ThemeType?=null,
    var type_12: ThemeType?=null,
    var type_30: ThemeType?=null,
    var type_31: ThemeType?=null,
    var type_32: ThemeType?=null,
    var type_33: ThemeType?=null,
    var type_34: ThemeType?=null,
    var type_40: ThemeType?=null,
    var type_200: ThemeType?=null
    ): Parcelable


@Parcelize
data class ThemeType(

    val text_color_1: String?=null,
    val text_color_2: String?=null,
    val text_color_3: String?=null,
    val text_color_4: String?=null,
    val text_color_5: String?=null,

    val tint_color_1: String?=null,
    val tint_color_2: String?=null,

    val card_color_1: String?=null,
    val card_color_2: String?=null,

    val view_color_1: String?=null,
    val view_color_2: String?=null,

    val border_color_1: String?=null,
    val border_color_2: String?=null,
    val bg_color_1: String?=null,
    val trans_bg_color: String?=null,
    val bg_1: String?=null

): Parcelable
/*
@Parcelize
data class ModeLight(
    val app_name: String,
    val bg_detail_1: String?=null,
    val bg_detail_2: String?=null,
    val bg_home_1: String?=null,
    val bg_home_2: String?=null,
    val bg_list_1: String?=null,
    val bg_list_2: String?=null,
    val bg_list_3: String?=null,
    val bg_splash_1: String?=null,
    val bg_splash_2: String?=null,
    val developer: String,
    val icon_app_1: String?=null,
    val icon_app_2: String?=null,
    val icon_developer_1: String?=null,
    val icon_developer_2: String?=null,

    val text_color_1: String?="#ffffff",
    val text_color_2: String?="#ffffff",
    val text_color_3: String?="#ffffff",
    val text_color_4: String?="#ffffff",
    val text_color_5: String?="#ffffff",

    val bg_color_1: String?="#000000",
    val bg_color_2: String?="#000000",
    val bg_list_color_1: String?="#000000",
    val bg_list_color_2: String?="#000000",
    val bg_detail_color_1: String?="#000000",
    val bg_detail_color_2: String?="#000000",
    val bg_home_color_1: String?="#000000",
    val bg_home_color_2: String?="#000000"


): Parcelable
*/



@Parcelize
data class MModeData(

    val bg_detail_1: String?=null,
    val bg_detail_2: String?=null,
    var isSelected: Boolean=false,
    val type: Int =0,
    val text1: String?=null,
    val text2: String?=null,
    val text1_color: String?="#000000",
    val text2_color: String?="#000000",
    val bg1_color: String?="#ffffff"
): Parcelable


data class MGsonJsonTest(var jsonrequest: JsonObject?=null, // There was an eror in processing please retry or contact support@beeone.co.uk
                         var jsonoutput: JsonObject?=null, var url: String?=null)

data class ApiFailure2(
    var where: String ?="",
    var obj: Any ?,
    var error: String ?="",
    val isPagintion: Boolean ?=false,
    val message : String ?="Error",
    val message2 : String ="",
    var isSessionExpired: Boolean =false

)

@Parcelize
data class MBaseDialog(

    var title: String?="", // 5.011000
    var desc: String?="", // 2
    var ok_text: String?="Ok", // 4556347952822508
    var cancel_text: String?="Cancel", // 5.011000
    var extra_text: String?="",
    var from: String?="",



    var html_text: Boolean?=false,
    var close_icon: Boolean?=true, // 2018-11-28 09:48:56
    var cancel_icon:  Boolean?=true

): Parcelable/*  var ok_click: View.OnClickListener,*/


data class MHomeScreenRoot(

    val status: Boolean=true,
    val in_app: Boolean=true,
    val message: String?=null,
    val errors: Any?=null,
    val browser_url: String?=null,
    val web_url: String?=null,
    val web_properties: MWeb?=null,
    val `data`: List<MHomeData>?=null,
    val `banner_data`: List<MBanner>?=null,
    val `quiz_data`: List<MQuiz>?=null,
    val next_url: String?=null
)

@Parcelize
data class MHomeData(
    val id: Int?=null,

    val text1: String?="",
    val text1_color: String?=null,
    val text_color_1: String?=null,
    val text_color_2: String?=null,
    val text_color_3: String?=null,
    val type: Int=-1,
    val span_count: Int=2,
    val banner: List<MBanner>?=null,

    val menu_card_list: List<MHomeData>?=null,
    val text2: String?=null,
    val text3: String?=null,
    val image1: String?="",

    val design_override: Boolean=false,
    val text2_color: String?=null,

    val detail_text1: String?=null,
    val detail_text1_color: String?=null,
    val detail_text2: String?=null,
    val detail_text2_color: String?=null,


    var time: Long=30,
    val icon_1: String?=null,
    val bg_1: String?=null,
    val bg_color: String?=null,
    val trans_bg_color: String?="#50000000",
    val browser_url: String?=null,
    val web_url: String?=null,
    val in_app: Boolean=true,
    val web_properties: MWeb?=null,

    val click: Boolean=false,
    val detail_url: String?=null,
    val is_manual_json: Boolean=false,
    val is_dynamic: Boolean=false
): Parcelable

@Parcelize
data class MBanner(
    val id: Int?=null,
    val browser_url: String?=null,
    val web_url: String?=null,
    val in_app: Boolean=true,
    val web_properties: MWeb?=null,

    val bg_1: String?="",
    val click: Boolean=false,
    val type: Int=-1,
    val text1: String?="",
    val text1_color: String?="#000000",
    val text2: String?="",
    val text2_color: String?="#000000",

    val detail_text1: String?=null,
    val detail_text1_color: String?="#000000",
    val detail_text2: String?=null,
    val detail_text2_color: String?="#000000",
    val design_override: Boolean=false,
    val bg_color: String?="#000000",
    val trans_bg_color: String?="#50000000"
): Parcelable


@Parcelize
data class MWeb(

    val close_icon: Boolean=true,
    val refresh_icon: Boolean=true,
    val actionbar: Boolean=true
): Parcelable



@Parcelize
data class MQuiz(
    val id: Int?=null,

    val type: Int=-1,
    val question: String,
    var your_answer: String?=null,
    var parent_postion: Int?=null,
    var is_answered: Boolean=false,
    val correct_answer: String,
    val choices: List<String>,
    val answer_text_color_1: String?="#000000",
    val question_text_color_1: String?="#000000",

    val design_override: Boolean=false,
    val bg_1: String?=null,
    val bg_color: String?=null,
    val trans_bg_color: String?=null
): Parcelable



@Parcelize
data class MItemData(
    val id: Int?=null,
    val category_id: Int?=null,
    val project_id: Int?=null,
    val template_id: Int=0,
    val image_url:  String?=null,
    val web_url:  String?=null,

    @SerializedName("name") val text1: String?=null,
    @SerializedName("description") val text2: String?=null,
    val custom_field: MCustomField?=null
): Parcelable

/*    val name: String?=null,
    val description: String?=null,*/
@Parcelize
data class MCustomField(
    val banner_list: List<MBanner>?=null,
    val design_override: Boolean=false,
    val design_properties: ThemeType?=null,
    val web_properties: MWeb?=null,
    val browser_url: String?=null,
    val web_url: String?=null,
    val in_app: Boolean=true,
    val clickable: Boolean=true
): Parcelable




@Parcelize
data class MMatch(
    var vice_captian: MDPlayer?=null,
    var captian: MDPlayer?=null,
    var player_list: ArrayList<MDPlayer>?=null,
    var player_list1: ArrayList<MDPlayer>?=null,
    var player_list2: ArrayList<MDPlayer>?=null,
    var team1: MDTeam?=null,
    var team2: MDTeam?=null,
    val design_override: Boolean=false,
    val design_properties: ThemeType?=null,
    val web_properties: MWeb?=null,
    val browser_url: String?=null,
    val web_url: String?=null,
    val in_app: Boolean=true,
    val clickable: Boolean=true
): Parcelable

@Parcelize
data class MDPlayer(
    var text1: String ="",
    var captian: Boolean=false,
    var vc_captian: Boolean=false,
    val id: Int?=null,
    var team: MDTeam?=null,
    val design_override: Boolean=false,
    val design_properties: ThemeType?=null,
    val web_properties: MWeb?=null,
    val browser_url: String?=null,
    val web_url: String?=null,
    val in_app: Boolean=true,
    val clickable: Boolean=true
): Parcelable

@Parcelize
data class MDTeam(
    var text1: String ="",
    var text2: String ="",
    var selected: Boolean=false,
    val id: Int?=null,
    var icon1: String ?="",
    val design_override: Boolean=false,
    val design_properties: ThemeType?=null,
    val web_properties: MWeb?=null,
    val browser_url: String?=null,
    val web_url: String?=null,
    val in_app: Boolean=true,
    val clickable: Boolean=true
): Parcelable


@Parcelize
data class MBaseDialog(

    var title: String?="", // 5.011000
    var desc: String?="", // 2
    var ok_text: String?="Ok", // 4556347952822508
    var cancel_text: String?="Cancel", // 5.011000
    var extra_text: String?="",
    var from: String?="",


    var html_text: Boolean?=false,
    var close_icon: Boolean?=true, // 2018-11-28 09:48:56
    var cancel_icon:  Boolean?=true,


    var icon_url: String?=null,
    var position: Int=-1,
    var upto_value: Double?=null
): Parcelable

/*@Parcelize
data class MDesign(

    val close_icon: Boolean=true,
    val refresh_icon: Boolean=true,
    val actionbar: Boolean=true
): Parcelable*/




/*@Parcelize
data class MCardMenu(
    val id: Int?=null,
    val browser_url: String?=null,
    val web_url: String?=null,
    val in_app: Boolean=true,
    val click: Boolean=false,
    val type: Int=-1,
    val bg_1: String?=null,
    val icon_1: String?=null,
    val text1: String?="",
    val text2: String?="",
    val text_color_1: String?=null,
    val text_color_2: String?=null,

    val design_override: Boolean=false,
    val bg_color: String?="#000000",
    val trans_bg_color: String?="#50000000",
    val detail_url: String?=null
): Parcelable*/



/*
data class MDataRoot(

    val status: Boolean=true,
    val in_app: Boolean=true,
    val message: String?=null,
    val errors: Any?=null,
    val browser_url: String?=null,
    val web_url: String?=null,

    val `data`: List<MData>?=null,
    val `banner_data`: List<MBanner>?=null,
    val next_url: String?=null
)

@Parcelize
data class MData(
    val id: Int?=null,
    val browser_url: String?=null,
    val web_url: String?=null,
    val in_app: Boolean=true,
    val click: Boolean=false,
    val type: Int=-1,
    val bg_1: String?=null,
    val icon_1: String?=null,
    val text1: String?=null,
    val text2: String?=null,
    val text3: String?=null,
    val text_color_1: String?=null,
    val text_color_2: String?=null,
    val text_color_3: String?=null,
    val design_override: Boolean=false,
    val bg_color: String?="#000000",
    val trans_bg_color: String?="#50000000",
    val detail_url: String?=null
): Parcelable
*/



/*    val browser_url: String?=null,
    val web_url: String?=null,
    val in_app: Boolean=true,
    val web_properties: MWeb?=null,*/