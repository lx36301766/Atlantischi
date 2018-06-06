package pl.atlantischi.mapadapter

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Created on 06/06/2018.
 *
 * @author lx
 */
class MapView : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun onCreate(savedInstanceState: Bundle?) {

    }

    fun onStart() {}

    fun onResume() {}

    fun onPause() {}

    fun onStop() {}

    fun onDestroy() {}

    fun onLowMemory() {}

    fun onSaveInstanceState(bundle: Bundle?) {}

}
