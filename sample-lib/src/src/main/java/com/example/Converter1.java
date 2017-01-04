/*
 * Copyright 2015 Agapsys Tecnologia Ltda-ME.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class Converter1 implements AttributeConverter<Object, String>{

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
