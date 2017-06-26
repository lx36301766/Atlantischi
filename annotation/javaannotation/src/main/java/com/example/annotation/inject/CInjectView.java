package com.example.annotation.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 21/03/2017.
 *
 * @author lx
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface CInjectView {

    int value();

}
