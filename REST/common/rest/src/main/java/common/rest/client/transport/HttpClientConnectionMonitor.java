package common.rest.client.transport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.HttpClientConnectionManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class HttpClientConnectionMonitor implements Runnable {
    private static final Log LOGGER = LogFactory.getLog(HttpClientConnectionMonitor.class);

    private final HttpClientConnectionManager m_connectionManager;
    private final long m_idleTimeoutMillis;

    private HttpClientConnectionMonitor(final HttpClientConnectionManager connectionManager,
                                        final long idleTimeoutMillis) {
        m_connectionManager = connectionManager;
        m_idleTimeoutMillis = idleTimeoutMillis;
    }

    @Override
    public void run() {
        try {
            m_connectionManager.closeExpiredConnections();
        } catch (final Exception e) {
            LOGGER.warn("Unable to close expired connections due to: " + e.getMessage(), e);
        }
        if (m_idleTimeoutMillis > 0) {
            try {
                m_connectionManager.closeIdleConnections(m_idleTimeoutMillis, TimeUnit.MILLISECONDS);
            } catch (final Exception e) {
                LOGGER.warn("Unable to close idle connections due to: " + e.getMessage(), e);
            }
        }
    }

    public static void spawn(final HttpClientConnectionManager connectionManager,
                             final long connectionMonitorIdleTimeoutMillis,
                             final long connectionMonitorRunIntervalMillis) {
        if (connectionMonitorRunIntervalMillis > 0) {

            final HttpClientConnectionMonitor idleConnectionMonitor =
                    new HttpClientConnectionMonitor(connectionManager, connectionMonitorIdleTimeoutMillis);
            Executors.newScheduledThreadPool(1, new ThreadFactory() {
                @Override
                public Thread newThread(final Runnable r) {
                    final Thread t = new Thread(r);
                    t.setDaemon(true);
                    t.setContextClassLoader(null);
                    return t;
                }
            }).scheduleWithFixedDelay(idleConnectionMonitor, connectionMonitorRunIntervalMillis,
                    connectionMonitorRunIntervalMillis, TimeUnit.MILLISECONDS);
        }
    }
}
