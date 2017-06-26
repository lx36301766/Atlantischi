package com.example.annotation.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 22/03/2017.
 *
 * @author lx
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface COnClick {

    int[] value();

}
