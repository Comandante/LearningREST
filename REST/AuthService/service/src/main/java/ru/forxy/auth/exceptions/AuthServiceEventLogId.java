package ru.forxy.auth.exceptions;

import ru.forxy.common.exceptions.EventLogBase;
import ru.forxy.common.exceptions.EventType;

public enum AuthServiceEventLogId implements EventLogBase {

    // -------------------------------------------------------------------
    // Business events
    // -------------------------------------------------------------------
    TokenNotFound(AuthServiceEventLogId.BASE_EVENT_LOG_ID, 404,
            "Token with tokenKey '%1$s' is not found.",
            EventType.InvalidInput),

    TokenAlreadyExists(AuthServiceEventLogId.BASE_EVENT_LOG_ID + 1, 400,
            "Token with tokenKey '%1$s' already exists.",
            EventType.InvalidInput),

    InvalidPageNumber(AuthServiceEventLogId.BASE_EVENT_LOG_ID + 4, 400,
            "Invalid page number provided: '%1$s'",
            EventType.InvalidInput),

    ClientNotFound(AuthServiceEventLogId.BASE_EVENT_LOG_ID + 6, 404,
            "Client with ID '%1$s' is not found.",
            EventType.InvalidInput),

    ClientAlreadyExists(AuthServiceEventLogId.BASE_EVENT_LOG_ID + 7, 400,
            "Client with ID '%1$s' already exists.",
            EventType.InvalidInput);

    private static final int BASE_EVENT_LOG_ID = 20000;

    private Level m_logLevel;
    private String m_formatString;
    private int m_eventId;
    private int m_responseId;
    private EventType m_eventType;

    private AuthServiceEventLogId(final int eventId, final int responseId, final String formatString, final EventType eventType) {
        this(eventId, responseId, Level.ERROR, formatString, eventType);
    }

    private AuthServiceEventLogId(final int eventId, final int responseId, final Level level, final String formatString,
                                  final EventType eventType) {
        m_eventId = eventId;
        m_responseId = responseId;
        m_logLevel = level;
        m_formatString = formatString;
        m_eventType = eventType;
    }

    @Override
    public int getEventId() {
        return m_eventId;
    }

    public int getResponseId() {
        return m_responseId;
    }

    @Override
    public Level getLogLevel() {
        return m_logLevel;
    }

    @Override
    public EventType getEventType() {
        return m_eventType;
    }

    public String getMessage(final Object... arguments) {
        return arguments != null && arguments.length > 0 ? String.format(m_formatString, arguments) : m_formatString;
    }
}