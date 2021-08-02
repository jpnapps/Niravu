package xyz.sangcomz.chameleon.model

import android.graphics.drawable.Drawable
import android.view.View

data class ButtonSettingBundle(val text: String? = null,
                               val textSize: Float? = null,
                               val textColor: Int? = null,
                               val backgroundColor: Int? = null,
                               val buttonDrawable: Drawable? = null,
                               val useButtonDrawable: Boolean = false,
                               val listener: ((View) -> Unit)? = null)