package io.github.unseenwizzard.sr5chargen.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DocumentSizeFilter extends DocumentFilter {
    private int maxSize = 100;

    public DocumentSizeFilter(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (fb.getDocument().getLength() + string.length() > this.maxSize) {
            return;
        }

        fb.insertString(offset, string, attr);

    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {


        if (fb.getDocument().getLength() + text.length() > this.maxSize) {
            return;
        }

        fb.insertString(offset, text, attrs);
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        fb.remove(offset, length);
    }
}
