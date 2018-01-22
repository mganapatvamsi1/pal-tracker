package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private List<TimeEntry> timeEntries = new ArrayList();
    private Long runningIdCounter = 1L;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(runningIdCounter++);
        timeEntries.add(timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        return timeEntries.stream().filter(timeEntry -> timeEntry.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<TimeEntry> list() {
        return timeEntries;
    }

    @Override
    public TimeEntry update(long id, TimeEntry updatedTimeEntry) {
        TimeEntry existingEntry = find(id);
        if(existingEntry == null)
            return null;

        updatedTimeEntry.setId(id);
        timeEntries.replaceAll( timeEntry -> timeEntry.getId() == id ? updatedTimeEntry : timeEntry);

        return updatedTimeEntry;
    }

    @Override
    public void delete(long id) {
        TimeEntry savedTimeEntry = this.find(id);
        timeEntries.remove(savedTimeEntry);
    }
}

