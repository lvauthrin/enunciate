package net.sf.enunciate.decorations.jaxb;

import net.sf.jelly.apt.decorations.declaration.DecoratedFieldDeclaration;
import com.sun.mirror.declaration.FieldDeclaration;

import javax.xml.bind.annotation.XmlTransient;

/**
 * A field accessor.
 *
 * @author Ryan Heaton
 */
public class JAXBFieldAccessorDeclaration extends DecoratedFieldDeclaration implements JAXBAccessorDeclaration {

  public JAXBFieldAccessorDeclaration(FieldDeclaration delegate) {
    super(delegate);

    if (getAnnotation(XmlTransient.class) != null) {
      throw new IllegalArgumentException("An xml-transient field cannot be an accessor.");
    }
  }

  public String getPropertyName() {
    return getSimpleName();
  }
}
