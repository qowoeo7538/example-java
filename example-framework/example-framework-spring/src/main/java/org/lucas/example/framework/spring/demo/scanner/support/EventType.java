package org.lucas.example.framework.spring.demo.scanner.support;

public enum EventType {
    EXPIRED {
        @Override
        public String getCode() {
            return "expired";
        }
    },

    DEL {
        @Override
        public String getCode() {
            return "del";
        }
    },

    SET {
        @Override
        public String getCode() {
            return "set";
        }
    };

    private static final String KEY_EVENT_FORMAT = "__keyevent@%s__:%s";

    public Topic generateTopic(int database) {
        if (database < 0) {
            return new PatternTopic(String.format(KEY_EVENT_FORMAT, "*", getCode()));
        }
        return new ChannelTopic(String.format(KEY_EVENT_FORMAT, database, getCode()));
    }

    public abstract String getCode();

}