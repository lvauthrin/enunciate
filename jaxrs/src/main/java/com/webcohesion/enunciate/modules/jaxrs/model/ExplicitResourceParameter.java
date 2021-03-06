/**
 * Copyright © 2006-2016 Web Cohesion (info@webcohesion.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webcohesion.enunciate.modules.jaxrs.model;


import com.webcohesion.enunciate.javac.javadoc.JavaDoc;
import com.webcohesion.enunciate.modules.jaxrs.EnunciateJaxrsContext;

/**
 * A resource parameter with explicit values.
 *
 * @author Ryan Heaton
 */
public class ExplicitResourceParameter extends ResourceParameter {

  private final String docValue;
  private final String paramName;
  private final ResourceParameterType type;

  public ExplicitResourceParameter(ResourceMethod method, String docValue, String paramName, ResourceParameterType type, EnunciateJaxrsContext context) {
    super(method, method);
    this.docValue = docValue;
    this.paramName = paramName;
    this.type = type;
  }

  @Override
  public JavaDoc getJavaDoc() {
    return new JavaDoc(docValue, null);
  }

  @Override
  public String getParameterName() {
    return paramName;
  }

  @Override
  public String getDefaultValue() {
    return null;
  }

  @Override
  public boolean isMatrixParam() {
    return this.type == ResourceParameterType.MATRIX;
  }

  @Override
  public boolean isQueryParam() {
    return this.type == ResourceParameterType.QUERY;
  }

  @Override
  public boolean isPathParam() {
    return this.type == ResourceParameterType.PATH;
  }

  @Override
  public boolean isCookieParam() {
    return this.type == ResourceParameterType.COOKIE;
  }

  @Override
  public boolean isHeaderParam() {
    return this.type == ResourceParameterType.HEADER;
  }

  @Override
  public boolean isFormParam() {
    return this.type == ResourceParameterType.FORM;
  }

  @Override
  public String getTypeName() {
    return this.type.toString().toLowerCase();
  }
}
