/*
 * Copyright (c) 2019 Flughafen München GmbH.
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
 */

package com.munichairport.freemarker.java8.config.timezone.strategy;

import java.time.ZoneId;

import com.munichairport.freemarker.java8.config.timezone.TimezoneStrategy;

/**
 * {@link TimezoneStrategy} which never transforms the {@link ZoneId} returning always the {@code input}.<br>
 * <br>
 * The one and only instance of this singleton can be obtained by {@link #INSTANCE}.
 *
 * @author Fritz Lumnitz
 */
public enum KeepingTimezoneStrategy implements TimezoneStrategy {

    /**
     * The one and only instance of {@link KeepingTimezoneStrategy}.
     */
    INSTANCE;

    /**
     * {@inheritDoc}
     * 
     * @return Always the {@code input}
     */
    @Override
    public ZoneId getUpdatedZone(final ZoneId input) {
        return input;
    }

}
