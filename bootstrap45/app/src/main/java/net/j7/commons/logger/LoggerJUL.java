package net.j7.commons.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.logging.Level;

public class LoggerJUL {

    public static Logger getLogger() {
        return getLogger(((new Throwable()).getStackTrace()[1]).getClass()) ;
    }

    public final static Logger getLogger(Class<?> clz) {
        return getLogger(clz, null);
    };

    public final static Logger getLogger(Class<?> clz, String revision) {
        return new JUL(java.util.logging.Logger.getLogger(clz.getName()));
    };

    public interface Logger {

        void info(Object msg);
        void info(Object msg, Throwable t);
        void info(Object msg, Throwable t, Object... args);
        void info(Object msg, Object... args);

        void warn(Object msg);
        void warn(Object msg, Throwable t);
        void warn(Object msg, Throwable t, Object... args);
        void warn(Object msg, Object... args);


        void error(Object msg);
        void error(Object msg, Throwable t);
        void error(Object msg, Throwable t, Object... args);
        void error(Object msg, Object... args);

        void debug(Object msg);
        void debug(Object msg, Throwable t);
        void debug(Object msg, Throwable t, Object... args);
        void debug(Object msg, Object... args);

        void trace(Object msg);
        void trace(Object msg, Throwable t);
        void trace(Object msg, Throwable t, Object... args);
        void trace(Object msg, Object... args);

        boolean isDebugEnabled();
        boolean isWarnEnabled();
        boolean isErrorEnabled();
        boolean isInfoEnabled();
        boolean isTraceEnabled();

    }

    private static class JUL implements Logger {

//		FINEST  -> TRACE
//		 FINER   -> DEBUG / **TRACE**
//		 FINE    -> DEBUG
//		 INFO    -> INFO
//		 WARNING -> WARN
//		 SEVERE  -> ERROR

        private java.util.logging.Logger log;

        private JUL (java.util.logging.Logger logger) {
            this.log = logger;
        }

        static String str(Throwable t) {
            StringWriter sw = new StringWriter();
            t.printStackTrace(new PrintWriter(sw));
            return sw.toString();
        }

        static String str(Object o) {return o != null ? o instanceof Throwable ? str((Throwable)o) : o.toString() : null; }

        public void info(Object msg) {
            if (isInfoEnabled()) log.info(str(msg));
        }

        public void info(Object msg, Throwable t) {
            if (isInfoEnabled()) log.log(Level.INFO, str(msg), t);
        }

        public void info(Object msg, Throwable t, Object... args) {
            if (isInfoEnabled()) log.log(Level.INFO, format(str(msg), args), t);
        }

        public void info(Object msg, Object... args) {
            if (isInfoEnabled()) log.info(format(str(msg), args));
        }

        public void warn(Object msg) {
            if (isWarnEnabled()) log.warning(str(msg));
        }

        public void warn(Object msg, Throwable t) {
            if (isWarnEnabled()) log.log(Level.WARNING, str(msg), t);
        }

        public void warn(Object msg, Throwable t, Object... args) {
            if (isWarnEnabled()) log.log(Level.WARNING, format(str(msg), args), t);
        }

        public void warn(Object msg, Object... args) {
            if (isWarnEnabled()) log.warning(format(str(msg), args));
        }

        public void error(Object msg) {
            if (isErrorEnabled()) log.severe(str(msg));
        }

        public void error(Object msg, Throwable t) {
            if (isErrorEnabled()) log.log(Level.SEVERE, str(msg), t);
        }

        public void error(Object msg, Throwable t, Object... args) {
            if (isErrorEnabled()) log.log(Level.SEVERE, format(str(msg), args), t);
        }

        public void error(Object msg, Object... args) {
            if (isErrorEnabled()) log.severe(format(str(msg), args));
        }

        public void debug(Object msg) {
            if (isDebugEnabled()) log.fine(str(msg));
        }

        public void debug(Object msg, Throwable t) {
            if (isDebugEnabled()) log.log(Level.FINE, str(msg), t);
        }

        public void debug(Object msg, Throwable t, Object... args) {
            if (isDebugEnabled()) log.log(Level.FINE, format(str(msg), args), t);
        }

        public void debug(Object msg, Object... args) {
            if (isDebugEnabled()) log.fine(format(str(msg), args));
        }

        public void trace(Object msg) {
            if (isTraceEnabled()) log.finest(str(msg));
        }

        public void trace(Object msg, Throwable t) {
            if (isTraceEnabled()) log.log(Level.FINEST, str(msg), t);
        }

        public void trace(Object msg, Throwable t, Object... args) {
            if (isTraceEnabled()) log.log(Level.FINEST, format(str(msg), args), t);
        }

        public void trace(Object msg, Object... args) {
            if (isTraceEnabled()) log.finest(format(str(msg), args));
        }

        public boolean isDebugEnabled() {
            return  log.isLoggable(Level.FINE);
        }

        public boolean isWarnEnabled() {
            return log.isLoggable(Level.WARNING);
        }

        public boolean isErrorEnabled() {
            return log.isLoggable(Level.SEVERE);
        }

        public boolean isInfoEnabled() {
            return log.isLoggable(Level.INFO);
        }

        public boolean isTraceEnabled() {
            return log.isLoggable(Level.FINEST);
        }

    }

    private static String preformat(String s, int max) {
        if (s==null || s.isEmpty()) return s;
        if (!s.contains("{}") && s.indexOf('\'') < 0) return s;
        char ch, lch = (char)0;
        int j = 0;
        boolean q = false;
        StringBuilder sb = new StringBuilder(s.length()+3);
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            if (ch == '}' && lch == '{' ) { sb.append(j); if (j<max) j++; }
            else if (ch == '\'') { q =! q; }
            else if (q) { q =! q; sb.append(lch); }

            sb.append(ch);lch = ch;
        }
        if (q) sb.append(lch);

        return sb.toString();
    }

    public static String format(String pattern, Object arg) {
        return format(pattern, new Object[] {arg});
    }

    public static String format(String pattern, Object[] args) {
        if (null == pattern || args == null || pattern.isEmpty() || args.length == 0) return pattern;
        return new MessageFormat(preformat(pattern, args.length-1)).format(args);
    }

}

