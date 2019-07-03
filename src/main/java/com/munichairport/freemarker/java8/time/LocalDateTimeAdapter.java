/*
 * Copyright (c) 2019 Flughafen München GmbH.
 *
 * Copyright (c) 2015-2015 Amedia Utvikling AS.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file was modified by Flughafen München GmbH in order to add
 * or change the following functionality:
 *  - Added configuration support
 */

package com.munichairport.freemarker.java8.time;

import static com.munichairport.freemarker.java8.time.DateTimeTools.METHOD_AFTER;
import static com.munichairport.freemarker.java8.time.DateTimeTools.METHOD_BEFORE;
import static com.munichairport.freemarker.java8.time.DateTimeTools.METHOD_EQUALS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.munichairport.freemarker.java8.config.Java8Configuration;

import freemarker.template.AdapterTemplateModel;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

/**
 * LocalDateTimeAdapter adds basic format support for {@link LocalDateTime} too FreeMarker 2.3.23 and above.
 */
public class LocalDateTimeAdapter extends AbstractAdapter<LocalDateTime> implements AdapterTemplateModel, TemplateScalarModel, TemplateHashModel {

    public LocalDateTimeAdapter(final LocalDateTime obj, final Java8Configuration configuration) {
        super(obj, configuration);
    }

    @Override
    public TemplateModel get(final String s) throws TemplateModelException {
        if (DateTimeTools.METHOD_FORMAT.equals(s)) {
            return new LocalDateTimeFormatter(getObject());
        } else if (DateTimeTools.METHOD_EQUALS.equals(s) || DateTimeTools.METHOD_AFTER.equals(s) || DateTimeTools.METHOD_BEFORE.equals(s)) {
            return new LocalDateTimeChecker(getObject(), s);
        }
        throw new TemplateModelException(DateTimeTools.METHOD_UNKNOWN_MSG + s);
    }

    public class LocalDateTimeFormatter extends AbstractFormatter<LocalDateTime> implements TemplateMethodModelEx {

        public LocalDateTimeFormatter(final LocalDateTime obj) {
            super(obj);
        }

        @Override
        public Object exec(final List list) throws TemplateModelException {
            return getObject().format(DateTimeTools.createDateTimeFormatter(list, 0, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
    }

    public class LocalDateTimeChecker extends AbstractChecker<LocalDateTime> implements TemplateMethodModelEx {

        private final String method;

        public LocalDateTimeChecker(final LocalDateTime obj, final String method) {
            super(obj);
            this.method = method;
        }

        @SuppressWarnings("Duplicates")
        @Override
        public Object exec(final List list) throws TemplateModelException {
            final LocalDateTimeAdapter adapter = (LocalDateTimeAdapter) list.get(0);
            switch (this.method) {
                case METHOD_EQUALS:
                    return getObject().isEqual(adapter.getObject());
                case METHOD_AFTER:
                    return getObject().isAfter(adapter.getObject());
                case METHOD_BEFORE:
                    return getObject().isBefore(adapter.getObject());
            }
            throw new TemplateModelException("method not implemented");
        }
    }
}