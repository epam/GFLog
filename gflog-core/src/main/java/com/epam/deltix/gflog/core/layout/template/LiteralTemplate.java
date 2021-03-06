package com.epam.deltix.gflog.core.layout.template;

import com.epam.deltix.gflog.core.LogRecord;
import com.epam.deltix.gflog.core.util.Formatting;
import com.epam.deltix.gflog.core.util.MutableBuffer;


final class LiteralTemplate extends Template {

    protected final String literal;

    LiteralTemplate(final String literal) {
        this.literal = literal;
    }

    @Override
    public int size(final LogRecord record) {
        return literal.length();
    }

    @Override
    public int format(final LogRecord record, final MutableBuffer buffer, final int offset) {
        return Formatting.formatAsciiCharSequence(literal, buffer, offset);
    }

}
