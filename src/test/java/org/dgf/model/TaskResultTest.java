package org.dgf.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class TaskResultTest {
    @Test
    public void testEqualsTaskResult() {
        EqualsVerifier.forClass(TaskResult.class).verify();
    }
}
