package net.luis.data.xml.io;

import net.luis.utils.annotation.IndicationInterface;

import java.lang.annotation.*;

/**
 *
 * @author Luis-St
 *
 */

@Inherited
@Documented
@IndicationInterface
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR})
public @interface XmlDeserializable {

}
