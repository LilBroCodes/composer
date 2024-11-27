package org.lilbrocodes.composer.api.note;

public record Note(int pitch, int length, Instrument instrument) {

    @Override
    public String toString() {
        return "Note <" +
                "p: " + pitch +
                ", l: " + length +
                ", i: " + instrument +
                '>';
    }
}
