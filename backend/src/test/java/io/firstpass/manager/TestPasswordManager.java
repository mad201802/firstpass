package io.firstpass.manager;

import io.firstpass.FirstPass;
import io.firstpass.database.models.CategoryModel;
import io.firstpass.manager.models.EntryModel;
import org.junit.jupiter.api.*;
import util.Utils;

import java.util.ArrayList;

public class TestPasswordManager {

    @BeforeEach()
    public void setup() {
        Utils.initPasswordManagerInstance();
    }

    @Test()
    public void test_default_database() {
        ArrayList<EntryModel> entries = FirstPass.passwordManager.getAllEntries();
        ArrayList<CategoryModel> categories = FirstPass.passwordManager.getAllCategories();

        Assertions.assertEquals(0, entries.size());

        Assertions.assertEquals(1, categories.size());
        Assertions.assertEquals("Uncategorized", categories.get(0).getCategory());
    }

    @Test()
    public void test_create_delete_entry() {
        // Test creating an entry
        int id = FirstPass.passwordManager.createEntry("test", "test", "test", 1, "test", "test");
        Assertions.assertEquals(1, id);

        ArrayList<EntryModel> entries = FirstPass.passwordManager.getAllEntries();
        Assertions.assertEquals(1, entries.size());

        EntryModel entry = entries.get(0);
        Assertions.assertEquals("test", entry.getName());
        Assertions.assertEquals("test", entry.getUsername());
        Assertions.assertEquals("test", entry.getPassword());
        Assertions.assertEquals(1, entry.getId());
        Assertions.assertEquals("test", entry.getUrl());
        Assertions.assertEquals("test", entry.getNotes());

        // Test deleting an entry
        FirstPass.passwordManager.deleteEntryById(id);
        entries = FirstPass.passwordManager.getAllEntries();
        Assertions.assertEquals(0, entries.size());
    }

    @Test()
    public void test_create_delete_without_category() {
        // Test creating a category
        int id = FirstPass.passwordManager.createCategory("test");
        Assertions.assertEquals(2, id);

        ArrayList<CategoryModel> categories = FirstPass.passwordManager.getAllCategories();
        Assertions.assertEquals(2, categories.size());

        CategoryModel category = categories.get(1);
        Assertions.assertEquals("test", category.getCategory());
        Assertions.assertEquals(2, category.getId());

        // Test creating an entry
        int entryId = FirstPass.passwordManager.createEntry("test", "test", "test", 2, "test", "test");

        // Test deleting a category
        FirstPass.passwordManager.deleteCategoryById(id, false);

        categories = FirstPass.passwordManager.getAllCategories();
        Assertions.assertEquals(1, categories.size());

        ArrayList<EntryModel> entries = FirstPass.passwordManager.getAllEntries();
        Assertions.assertEquals(1, entries.size());
        Assertions.assertEquals(1, entries.get(0).getId());
    }

    @Test()
    public void test_delete_category_with_entries() {
        // Test creating a category
        int id = FirstPass.passwordManager.createCategory("test");
        Assertions.assertEquals(2, id);

        ArrayList<CategoryModel> categories = FirstPass.passwordManager.getAllCategories();
        Assertions.assertEquals(2, categories.size());

        CategoryModel category = categories.get(1);
        Assertions.assertEquals("test", category.getCategory());
        Assertions.assertEquals(2, category.getId());

        // Test creating an entry
        int entryId = FirstPass.passwordManager.createEntry("test", "test", "test", 2, "test", "test");

        // Test deleting a category
        FirstPass.passwordManager.deleteCategoryById(id, true);

        categories = FirstPass.passwordManager.getAllCategories();
        Assertions.assertEquals(1, categories.size());

        ArrayList<EntryModel> entries = FirstPass.passwordManager.getAllEntries();
        Assertions.assertEquals(0, entries.size());
    }

    @Test()
    public void test_update_category() {
        // Test creating a category
        int id = FirstPass.passwordManager.createCategory("test");
        Assertions.assertEquals(2, id);

        ArrayList<CategoryModel> categories = FirstPass.passwordManager.getAllCategories();
        Assertions.assertEquals(2, categories.size());

        CategoryModel category = categories.get(1);
        Assertions.assertEquals("test", category.getCategory());
        Assertions.assertEquals(2, category.getId());

        // Test updating a category
        FirstPass.passwordManager.updateCategory(id, "test2");

        categories = FirstPass.passwordManager.getAllCategories();
        Assertions.assertEquals(2, categories.size());

        category = categories.get(1);
        Assertions.assertEquals("test2", category.getCategory());
        Assertions.assertEquals(2, category.getId());
    }

    @Test()
    public void test_get_entry_by_id() {
        // Test creating an entry
        int id = FirstPass.passwordManager.createEntry("test", "test", "test", 1, "test", "test");
        Assertions.assertEquals(1, id);

        EntryModel entry = FirstPass.passwordManager.getEntryById(id);
        Assertions.assertEquals("test", entry.getName());
        Assertions.assertEquals("test", entry.getUsername());
        Assertions.assertEquals("test", entry.getPassword());
        Assertions.assertEquals(1, entry.getId());
        Assertions.assertEquals("test", entry.getUrl());
        Assertions.assertEquals("test", entry.getNotes());
    }

    @Test()
    public void test_get_all_entries_by_id() {
        // Test creating an entry
        int id = FirstPass.passwordManager.createEntry("test", "test", "test", 1, "test", "test");
        Assertions.assertEquals(1, id);

        ArrayList<EntryModel> entries = FirstPass.passwordManager.getAllEntries();
        Assertions.assertEquals(1, entries.size());

        EntryModel entry = entries.get(0);
        Assertions.assertEquals("test", entry.getName());
        Assertions.assertEquals("test", entry.getUsername());
        Assertions.assertEquals("test", entry.getPassword());
        Assertions.assertEquals(1, entry.getId());
        Assertions.assertEquals("test", entry.getUrl());
        Assertions.assertEquals("test", entry.getNotes());

        // Test creating an entry
        int id2 = FirstPass.passwordManager.createEntry("test2", "test2", "test2", 1, "test2", "test2");
        Assertions.assertEquals(2, id2);

        entries = FirstPass.passwordManager.getAllEntries();
        Assertions.assertEquals(2, entries.size());

        entry = entries.get(1);
        Assertions.assertEquals("test2", entry.getName());
        Assertions.assertEquals("test2", entry.getUsername());
        Assertions.assertEquals("test2", entry.getPassword());
        Assertions.assertEquals(2, entry.getId());
        Assertions.assertEquals("test2", entry.getUrl());
        Assertions.assertEquals("test2", entry.getNotes());

        // Test getting all entries by id
        entries = FirstPass.passwordManager.getAllEntriesByCategory(1);
        Assertions.assertEquals(2, entries.size());

        entry = entries.get(0);
        Assertions.assertEquals("test", entry.getName());
        Assertions.assertEquals("test", entry.getUsername());
        Assertions.assertEquals("test", entry.getPassword());
        Assertions.assertEquals(1, entry.getId());
        Assertions.assertEquals("test", entry.getUrl());
        Assertions.assertEquals("test", entry.getNotes());

        entry = entries.get(1);
        Assertions.assertEquals("test2", entry.getName());
        Assertions.assertEquals("test2", entry.getUsername());
        Assertions.assertEquals("test2", entry.getPassword());
        Assertions.assertEquals(2, entry.getId());
        Assertions.assertEquals("test2", entry.getUrl());
        Assertions.assertEquals("test2", entry.getNotes());
    }

    @Test()
    public void test_update_entry() {
        // Test creating an entry
        int id = FirstPass.passwordManager.createEntry("test", "test", "test", 1, "test", "test");
        Assertions.assertEquals(1, id);

        ArrayList<EntryModel> entries = FirstPass.passwordManager.getAllEntries();
        Assertions.assertEquals(1, entries.size());

        EntryModel entry = entries.get(0);
        Assertions.assertEquals("test", entry.getName());
        Assertions.assertEquals("test", entry.getUsername());
        Assertions.assertEquals("test", entry.getPassword());
        Assertions.assertEquals(1, entry.getId());
        Assertions.assertEquals("test", entry.getUrl());
        Assertions.assertEquals("test", entry.getNotes());

        // Test updating an entry
        FirstPass.passwordManager.updateEntry(id, "test2", "test2", "test2", 1, "test2", "test2");

        entries = FirstPass.passwordManager.getAllEntries();
        Assertions.assertEquals(1, entries.size());

        entry = entries.get(0);
        Assertions.assertEquals("test2", entry.getName());
        Assertions.assertEquals("test2", entry.getUsername());
        Assertions.assertEquals("test2", entry.getPassword());
        Assertions.assertEquals(1, entry.getId());
        Assertions.assertEquals("test2", entry.getUrl());
        Assertions.assertEquals("test2", entry.getNotes());
    }

    @Test()
    public void test_delete_entry() {
        // Test creating an entry
        int id = FirstPass.passwordManager.createEntry("test", "test", "test", 1, "test", "test");
        Assertions.assertEquals(1, id);

        ArrayList<EntryModel> entries = FirstPass.passwordManager.getAllEntries();
        Assertions.assertEquals(1, entries.size());

        EntryModel entry = entries.get(0);
        Assertions.assertEquals("test", entry.getName());
        Assertions.assertEquals("test", entry.getUsername());
        Assertions.assertEquals("test", entry.getPassword());
        Assertions.assertEquals(1, entry.getId());
        Assertions.assertEquals("test", entry.getUrl());
        Assertions.assertEquals("test", entry.getNotes());

        // Test creating an entry
        int id2 = FirstPass.passwordManager.createEntry("test2", "test2", "test2", 1, "test2", "test2");
        Assertions.assertEquals(2, id2);

        entries = FirstPass.passwordManager.getAllEntries();
        Assertions.assertEquals(2, entries.size());

        entry = entries.get(1);
        Assertions.assertEquals("test2", entry.getName());
        Assertions.assertEquals("test2", entry.getUsername());
        Assertions.assertEquals("test2", entry.getPassword());
        Assertions.assertEquals(2, entry.getId());
        Assertions.assertEquals("test2", entry.getUrl());
        Assertions.assertEquals("test2", entry.getNotes());

        // Test deleting an entry
        FirstPass.passwordManager.deleteEntryById(1);

        entries = FirstPass.passwordManager.getAllEntries();
        Assertions.assertEquals(1, entries.size());

        entry = entries.get(0);
        Assertions.assertEquals("test2", entry.getName());
        Assertions.assertEquals("test2", entry.getUsername());
        Assertions.assertEquals("test2", entry.getPassword());
        Assertions.assertEquals(2, entry.getId());
        Assertions.assertEquals("test2", entry.getUrl());
        Assertions.assertEquals("test2", entry.getNotes());
    }

    @AfterEach()
    public void teardown() {
        Utils.teardown();
    }

}
