package pl.atlantischi.androidannotation.runtime.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 22/03/2017.
 *
 * @author lx
 */

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Event {

    Class<?> type();

    String setter();

    String methodName();

}
