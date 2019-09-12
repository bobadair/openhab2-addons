/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.lutron.internal.handler;

import org.eclipse.smarthome.core.thing.Thing;
import org.openhab.binding.lutron.internal.KeypadComponent;
import org.openhab.binding.lutron.internal.discovery.project.ComponentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler responsible for communicating with the virtual buttons on the RadioRA2 main repeater
 *
 * @author Bob Adair - Initial contribution
 */
public class VirtualKeypadHandler extends BaseKeypadHandler {

    private class Component implements KeypadComponent {
        private final int id;
        private final String channel;
        private final String description;
        private final ComponentType type;

        Component(int id, String channel, String description, ComponentType type) {
            this.id = id;
            this.channel = channel;
            this.description = description;
            this.type = type;
        }

        @Override
        public int id() {
            return id;
        }

        @Override
        public String channel() {
            return channel;
        }

        @Override
        public String description() {
            return description;
        }

        @Override
        public ComponentType type() {
            return type;
        }
    }

    private final Logger logger = LoggerFactory.getLogger(VirtualKeypadHandler.class);

    @Override
    protected boolean isLed(int id) {
        return (id >= 101 && id <= 200);
    }

    @Override
    protected boolean isButton(int id) {
        return (id >= 1 && id <= 100);
    }

    @Override
    protected boolean isCCI(int id) {
        return false;
    }

    @Override
    protected void configureComponents(String model) {
        logger.debug("Configuring components for virtual keypad");

        for (int x = 1; x <= 100; x++) {
            buttonList.add(new Component(x, String.format("button%d", x), "Virtual Button", ComponentType.BUTTON));
            ledList.add(new Component(x + 100, String.format("led%d", x), "Virtual LED", ComponentType.LED));
        }
    }

    public VirtualKeypadHandler(Thing thing) {
        super(thing);
        // Mark all channels "Advanced" since most are unlikely to be used in any particular config
        advancedChannels = true;
    }

}
