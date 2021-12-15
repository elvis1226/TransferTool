package org.dgf.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class TaskTest {
    @Test
    public void testEqualsTask() {
        EqualsVerifier.forClass(Task.class).verify();
    }
}
