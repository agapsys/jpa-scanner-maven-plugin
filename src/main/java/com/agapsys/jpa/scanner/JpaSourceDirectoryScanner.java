/*
 * Copyright 2016 Agapsys Tecnologia Ltda-ME.
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
package com.agapsys.jpa.scanner;

import static com.agapsys.jpa.scanner.JpaScannerDefs.log;
import com.agapsys.mvn.scanner.SourceDirectoryScanner;
import com.agapsys.mvn.scanner.parser.AnnotationInfo;
import com.agapsys.mvn.scanner.parser.ClassInfo;
import com.agapsys.mvn.scanner.parser.ParsingException;
import com.agapsys.mvn.scanner.parser.Visibility;

/**
 * JPA Implementation of Source Directory Scanner
 */
public class JpaSourceDirectoryScanner extends SourceDirectoryScanner {
    // STATIC SCOPE ============================================================
    private static JpaSourceDirectoryScanner SINGLETON = new JpaSourceDirectoryScanner();

    public static JpaSourceDirectoryScanner getInstance() {
        return SINGLETON;
    }

    private static boolean containsAnnotationClass(ClassInfo classInfo, String annotationClassName) {
        for (AnnotationInfo annotationInfo : classInfo.annotations) {
            if (annotationInfo.className.equals(annotationClassName))
                return true;
        }

        return false;
    }
    // =========================================================================

    // INSTANCE SCOPE ==========================================================
    private JpaSourceDirectoryScanner() {}

    private static final String ENTITY_ANNOTATION_CLASS     = "javax.persistence.Entity";
    private static final String CONVERTER_ANNOTATION_CLASS = "javax.persistence.Converter";

    @Override
    protected boolean shallBeIncluded(ClassInfo classInfo) throws ParsingException {
        boolean accessible = true;

        ClassInfo currentClassInfo = classInfo;
        do {
            if (currentClassInfo.visibility != Visibility.PUBLIC) {
                accessible = false;
                break;
            }

            if (currentClassInfo.containerClass != null && !currentClassInfo.isStaticNested) {
                accessible = false;
                break;
            }

            currentClassInfo = currentClassInfo.containerClass;
        } while (currentClassInfo != null);

        if (!accessible)
            return false;

        boolean hasEntityAnnotation = containsAnnotationClass(classInfo, ENTITY_ANNOTATION_CLASS);
        boolean isConverter = containsAnnotationClass(classInfo, CONVERTER_ANNOTATION_CLASS);

        return (hasEntityAnnotation || isConverter);
    }

    @Override
    protected void beforeInclude(ClassInfo classInfo) {
        log("Detected JPA class: %s", classInfo.className);
    }
    // =========================================================================
}
