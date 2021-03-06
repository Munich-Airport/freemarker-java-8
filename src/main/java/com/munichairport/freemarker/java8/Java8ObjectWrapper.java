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
 *  - Removed Duration and Period adapters, as they do not give any
 *    additional functionality to default bean model
 */

package com.munichairport.freemarker.java8;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.munichairport.freemarker.java8.config.Java8Configuration;
import com.munichairport.freemarker.java8.config.timezone.strategy.EnvironmentTimezoneStrategy;
import com.munichairport.freemarker.java8.time.ClockAdapter;
import com.munichairport.freemarker.java8.time.InstantAdapter;
import com.munichairport.freemarker.java8.time.LocalDateAdapter;
import com.munichairport.freemarker.java8.time.LocalDateTimeAdapter;
import com.munichairport.freemarker.java8.time.LocalTimeAdapter;
import com.munichairport.freemarker.java8.time.MonthDayAdapter;
import com.munichairport.freemarker.java8.time.OffsetDateTimeAdapter;
import com.munichairport.freemarker.java8.time.OffsetTimeAdapter;
import com.munichairport.freemarker.java8.time.YearAdapter;
import com.munichairport.freemarker.java8.time.YearMonthAdapter;
import com.munichairport.freemarker.java8.time.ZoneIdAdapter;
import com.munichairport.freemarker.java8.time.ZoneOffsetAdapter;
import com.munichairport.freemarker.java8.time.ZonedDateTimeAdapter;

import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.Version;

/**
 * Freemarker ObjectWrapper that extends the DefaultObjectWrapper with support for all classes in the new java.time api.
 */
public class Java8ObjectWrapper extends DefaultObjectWrapper {

    private final Java8Configuration configuration;

    public Java8ObjectWrapper(final Version incompatibleImprovements, final Java8Configuration configuration) {
        super(incompatibleImprovements);
        this.configuration = Objects.requireNonNull(configuration, "configuration");
    }

    /**
     * Creates a new instance, with a {@link Java8Configuration} with
     * {@link Java8Configuration#setTimezoneStrategy(com.munichairport.freemarker.java8.config.timezone.TimezoneStrategy) timezoneStrategy} set to
     * {@link EnvironmentTimezoneStrategy}.
     *
     * @deprecated Uses NOT the {@link Java8Configuration#defaultConfiguration()}. New implementations should use {@link #Java8ObjectWrapper(Version, Java8Configuration)}
     */
    @Deprecated
    public Java8ObjectWrapper(final Version incompatibleImprovements) {
        this(incompatibleImprovements, Java8Configuration.builder().timezoneStrategy(EnvironmentTimezoneStrategy.INSTANCE).build());
    }

    @Override
    protected TemplateModel handleUnknownType(final Object obj) throws TemplateModelException {
        if (obj instanceof Clock) {
            return new ClockAdapter((Clock) obj, getConfiguration(), this);
        } else if (obj instanceof Instant) {
            return new InstantAdapter((Instant) obj, getConfiguration(), this);
        } else if (obj instanceof LocalDate) {
            return new LocalDateAdapter((LocalDate) obj, getConfiguration(), this);
        } else if (obj instanceof LocalDateTime) {
            return new LocalDateTimeAdapter((LocalDateTime) obj, getConfiguration(), this);
        } else if (obj instanceof LocalTime) {
            return new LocalTimeAdapter((LocalTime) obj, getConfiguration(), this);
        } else if (obj instanceof MonthDay) {
            return new MonthDayAdapter((MonthDay) obj, getConfiguration(), this);
        } else if (obj instanceof OffsetDateTime) {
            return new OffsetDateTimeAdapter((OffsetDateTime) obj, getConfiguration(), this);
        } else if (obj instanceof OffsetTime) {
            return new OffsetTimeAdapter((OffsetTime) obj, getConfiguration(), this);
        } else if (obj instanceof Year) {
            return new YearAdapter((Year) obj, getConfiguration(), this);
        } else if (obj instanceof YearMonth) {
            return new YearMonthAdapter((YearMonth) obj, getConfiguration(), this);
        } else if (obj instanceof ZonedDateTime) {
            return new ZonedDateTimeAdapter((ZonedDateTime) obj, getConfiguration(), this);
        } else if (obj instanceof ZoneOffset) {
            return new ZoneOffsetAdapter((ZoneOffset) obj, getConfiguration(), this);
        } else if (obj instanceof ZoneId) {
            return new ZoneIdAdapter((ZoneId) obj, getConfiguration(), this);
        }
        return super.handleUnknownType(obj);
    }

    public Java8Configuration getConfiguration() {
        return this.configuration;
    }
}
