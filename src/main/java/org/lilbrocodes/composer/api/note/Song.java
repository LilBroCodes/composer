package org.lilbrocodes.composer.api.note;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a musical song consisting of a list of {@link Note} objects.
 * Provides methods for adding notes, retrieving notes, calculating song length,
 * and finding the note to play at a given tick.
 */
public class Song {
    private final List<Note> notes;
    private int length;

    /**
     * Creates an empty song.
     */
    public Song() {
        this.notes = new ArrayList<>();
        this.length = 0;
    }

    /**
     * Creates a song from the provided list of notes.
     *
     * @param notes The list of {@link Note} objects that make up the song.
     */
    public Song(List<Note> notes) {
        this.notes = notes;
        this.length = notes.size();
    }

    /**
     * Adds a new note to the song and updates the length of the song.
     *
     * @param note The {@link Note} to be added to the song.
     */
    public void addNote(Note note) {
        notes.add(note);
        length = notes.size();
    }

    /**
     * Retrieves the list of notes in the song.
     *
     * @return A {@link List} of {@link Note} objects.
     */
    public List<Note> getNotes() {
        return notes;
    }

    /**
     * Retrieves the length of the song, which is the number of notes it contains.
     *
     * @return The number of notes in the song.
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns a string representation of the song, including each note in the song.
     *
     * @return A string describing the song.
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Song <");
        for (Note note : this.notes) {
            out.append("; ").append(note.toString());
        }
        return out.append(">").toString();
    }

    /**
     * Calculates the total length of the song, including the specified padding between notes.
     *
     * @param padding The padding to be added between notes (in ticks).
     * @return The total length of the song with padding.
     */
    public int getTotalLength(int padding) {
        int out = 0;
        for (Note note : notes) {
            out += note.length();
        }
        return out += padding * 2;
    }

    /**
     * Calculates the total length of the song (without padding).
     *
     * @return The total length of the song.
     */
    public int getTotalLength() {
        int out = 0;
        for (Note note : notes) {
            out += note.length();
        }
        return out;
    }

    /**
     * Finds the note to play at a given tick in the song.
     *
     * @param tick The current tick in the song's timeline.
     * @return The {@link Note} to play at the given tick, or {@code null} if no note is played at this tick.
     */
    @Nullable
    public Note getNoteAtTick(int tick) {
        if (tick == 0) return notes.get(0);
        int elapsedTicks = 0;

        for (Note note : notes.subList(1, notes.size())) {
            elapsedTicks += note.length();
            if (tick == elapsedTicks) return note;
        }

        return null;
    }
}
