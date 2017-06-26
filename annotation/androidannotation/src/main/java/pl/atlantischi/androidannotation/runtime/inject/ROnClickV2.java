package pl.atlantischi.androidannotation.runtime.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.view.View;

/**
 * Created on 22/03/2017.
 *
 * @author lx
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Event(type = View.OnClickListener.class, setter = "setOnClickListener", methodName = "onClick")
public @interface ROnClickV2 {

    int[] value();

}
