/*
 * Copyright (C) 2022 DV Bern AG, Switzerland
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
package ch.dvbern.oss.construct.xml;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;


/**
 * Implementation of {@code ElementParser}. Responsible for parsing
 * xml-tags with the element-name "invoke" ({@code <invoke  >}).
 * The parser may use other {@code ElementParser} instances for parsing
 * nested elements.
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 */
public class InvokeParser implements ElementParser {

	/**
	 * Method parses the passed xml-element and creates an object based on the
	 * information defined by the xml-tag.
	 *
	 * @param element containing the information of the parsed xml-element
	 * @param factory ParserFactory returning the parsers for parsing nested
	 * tags
	 * @return ClassObjectPair: parsed xml-data, never null.
	 * @throws ElementParserException Thrown, if a problem occurs while
	 *                                parsing the xml-tag and creating the class/object
	 *                                instances.
	 */
	@Override
    @NonNull
    public ClassObjectPair parse(@NonNull Element element, @NonNull ParserFactory factory)
			throws ElementParserException {

        String methodName = Optional
            .ofNullable(element.getAttribute("methodName"))
            .orElseThrow(() -> new ElementParserException("methodName attribute is missing"));

        var objectElChildren = element.getElementsByTagName("target");
		if (objectElChildren.size() != 1) {
			throw new ElementParserException(
					"object must have exactly on child (construct or ref or cast...)");
		}
        Element objectElToParse = objectElChildren.get(0);
		Object myObject;
		Class<?> myClass;
		try {
			ClassObjectPair cop = factory.getParser(
					objectElToParse.getNodeName()).parse(
					objectElToParse,
					factory);
            myObject = cop.getObject();
            myClass = cop.getKlass();
		} catch (ParserNotRegisteredException ex) {
			throw new ElementParserException(ex);
		}

        var parameterElList = element.getElementsByTagName("parameters");
		Class<?>[] classArray = new Class[parameterElList.size()];
		Object[] objArray = new Object[parameterElList.size()];
		for (int i = 0; i < parameterElList.size(); i++) {
            Element paramEl = parameterElList.get(i);
			try {
				ClassObjectPair cop = factory.getParser(
						paramEl.getNodeName()).parse(paramEl, factory);
                classArray[i] = cop.getKlass();
                objArray[i] = cop.getObject();
			} catch (ParserNotRegisteredException ex) {
				throw new ElementParserException(ex);
			}
		}

		try {
			Method method = myClass.getMethod(methodName, classArray);
			Object obj = method.invoke(myObject, objArray);
			if (obj == null) {
				return new ClassObjectPair(Object.class, null);
			}
			return new ClassObjectPair(obj.getClass(), obj);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
			throw new ElementParserException(ex);
		}

	}
}
