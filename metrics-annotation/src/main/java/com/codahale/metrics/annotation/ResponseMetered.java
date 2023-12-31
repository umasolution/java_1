package com.codahale.metrics.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation for marking a method of an annotated object as metered.
 * <p>
 * Given a method like this:
 * <pre><code>
 *     {@literal @}ResponseMetered(name = "fancyName", level = ResponseMeteredLevel.ALL)
 *     public String fancyName(String name) {
 *         return "Sir Captain " + name;
 *     }
 * </code></pre>
 * <p>
 * Meters for the defining class with the name {@code fancyName} will be created for response codes
 * based on the ResponseMeteredLevel selected. Each time the {@code #fancyName(String)} method is invoked,
 * the appropriate response meter will be marked.
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
public @interface ResponseMetered {
    /**
     * @return The name of the meter.
     */
    String name() default "";

    /**
     * @return If {@code true}, use the given name as an absolute name. If {@code false}, use the given name
     * relative to the annotated class. When annotating a class, this must be {@code false}.
     */
    boolean absolute() default false;

    /**
     * @return the ResponseMeteredLevel which decides which response code meters are marked.
     */
    ResponseMeteredLevel level() default ResponseMeteredLevel.COARSE;
}
