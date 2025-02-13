package com.Communication;

import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

public enum AppMsg {
    ONLYDIGITS(new SysMsg() {
        @Override
        public String getMessage() {
            return "Only exepted digits as inputs";
        }

        @Override
        public TrayIcon.MessageType getType() {
            return MessageType.ERROR;
        }

        @Override
        public int getTime() {
            return Messages.TIME;
        }
    }),
    ONLYALPH(new SysMsg() {
        @Override
        public String getMessage() {
            return "Only exepted Alphebetic keys as inputs";
        }

        @Override
        public TrayIcon.MessageType getType() {
            return MessageType.ERROR;
        }

        @Override
        public int getTime() {
            return Messages.TIME;
        }
    }),
    NOSYMBOLS(new SysMsg() {
        @Override
        public String getMessage() {
            return "No symbols excepted as inputs";
        }

        @Override
        public TrayIcon.MessageType getType() {
            return MessageType.ERROR;
        }

        @Override
        public int getTime() {
            return Messages.TIME;
        }
    });
    private final SysMsg AM;

    private AppMsg(SysMsg AM) {
        this.AM = AM;
    }

    public SysMsg getMessage() {
        return AM;
    }

}
