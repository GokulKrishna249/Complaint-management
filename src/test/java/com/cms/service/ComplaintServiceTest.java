package com.cms.service;

import com.cms.model.Complaint;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ComplaintService (in-memory store).
 */
class ComplaintServiceTest {

    private ComplaintService service;

    @BeforeEach
    void setUp() {
        service = new ComplaintService();
    }

    @Test
    @DisplayName("submit: blank title returns error")
    void submit_blankTitle_returnsError() {
        String error = service.submit("", "desc", "Network", "HIGH", 3, "Gokul");
        assertNotNull(error);
        assertTrue(error.contains("Title"));
    }

    @Test
    @DisplayName("submit: blank description returns error")
    void submit_blankDescription_returnsError() {
        String error = service.submit("Title", "", "Network", "HIGH", 3, "Gokul");
        assertNotNull(error);
        assertTrue(error.contains("Description"));
    }

    @Test
    @DisplayName("submit: valid input returns null (success)")
    void submit_validInput_returnsNull() {
        String error = service.submit("Slow internet", "Drops often",
                "Network", "HIGH", 3, "Gokul");
        assertNull(error, "Expected no error for valid input");
    }

    @Test
    @DisplayName("updateStatus: valid status update succeeds")
    void updateStatus_validId_succeeds() {
        // Seed data has complaint id=1
        String error = service.updateStatus(1, "IN_PROGRESS", "Looking into it");
        assertNull(error);
    }

    @Test
    @DisplayName("updateStatus: non-existent id returns error")
    void updateStatus_invalidId_returnsError() {
        String error = service.updateStatus(9999, "RESOLVED", "done");
        assertNotNull(error);
        assertTrue(error.contains("not found"));
    }

    @Test
    @DisplayName("getByCustomer: returns complaints for customer id=3")
    void getByCustomer_returnsCorrectList() {
        var list = service.getByCustomer(3);
        assertFalse(list.isEmpty());
        list.forEach(c -> assertEquals(3, c.getCustomerId()));
    }

    @Test
    @DisplayName("countByStatus: OPEN count is non-negative")
    void countByStatus_openIsNonNegative() {
        long count = service.countByStatus(Complaint.Status.OPEN);
        assertTrue(count >= 0);
    }
}
