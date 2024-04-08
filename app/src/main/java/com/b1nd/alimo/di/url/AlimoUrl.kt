package com.b1nd.alimo.di.url

import com.b1nd.alimo.BuildConfig

object AlimoUrl {
    private const val ALIMO_URL = BuildConfig.SERVER_URL


    const val EMOJI = "$ALIMO_URL/emoji"
    const val COMMENT = "$ALIMO_URL/comment"
    const val NOTIFICATION = "$ALIMO_URL/notification"
    const val SIGN_IN = "$ALIMO_URL/sign-in"
    const val REFRESH = "$ALIMO_URL/refresh"
    const val CHILD_CODE = "$ALIMO_URL/verify-childCode"
    const val SIGN_UP = "$ALIMO_URL/sign-up"
    const val MEMBER = "$ALIMO_URL/member"
    const val CATEGORY = "$ALIMO_URL/category"
    const val BOOKMARK = "$ALIMO_URL/bookmark"
    const val IMAGE = "$ALIMO_URL/image/get"


    object Emoji{
        const val STATUS = "$EMOJI/status"
        const val LOAD = "$EMOJI/load"
    }

    object Comment{
        const val CREATE = "$COMMENT/create"
    }

    object Notification{
        const val SPEAKER = "$NOTIFICATION/speaker"
        const val READ = "$NOTIFICATION/read"
        const val LOAD = "$NOTIFICATION/load"
    }

    object SignIn{
        const val DODAM_SIGN_IN = "$SIGN_IN/dodam"
    }


    object Member{
        const val POST_EMAIL = "$MEMBER/emails/verification-requests"
        const val ALARM = "$MEMBER/alarm"
        const val STUDENT_SEARCH = "$MEMBER/student-search"
        const val INFO = "$MEMBER/info"
        const val GET_EMAIL = "$MEMBER/emails/verification"
        const val DELETE = "$MEMBER"
    }

    object Category{
        const val MEMBER_LIST = "$CATEGORY/list/member"
    }

    object Bookmark{
        const val PATCH = "$BOOKMARK/patch"
        const val LOAD = "$BOOKMARK/load"
    }


}